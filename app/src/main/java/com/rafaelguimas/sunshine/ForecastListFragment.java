package com.rafaelguimas.sunshine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastListFragment extends Fragment {

    private ArrayList<Forecast> forecasts = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    public ForecastListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout de item da lista
        View view = inflater.inflate(R.layout.fragment_forecast_list, container, false);

        // Preenche a lista de forecasts com os dados da API
        getForecastFromAPI();

        // Busca a RV da tela e define seu layout
        RecyclerView rvForecast = (RecyclerView) view.findViewById(R.id.rv_forecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(getContext()));

        // Define o adapter da RV
        adapter = new ForecastListAdapter(getContext(), forecasts);
        rvForecast.setAdapter(adapter);

        return view;
    }

    public void getForecastFromAPI() {
        // Lat e long de BH
        double lat = -19.918201;
        double lon = -43.9687357;
        // Periodo de uma semana
        int days = 7;
        // Monta a URL de conexao com a API
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+ lat +"&lon="+ lon +"&cnt="+ days +"&mode=json&appid=743819d3731cc9cddadbcba30382cc44&units=metric";

        // Requisicao dos dados atraves da biblioteca Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Define o nome da cidade na actionbar
                try {
                    JSONObject jsonObjectCity = response.getJSONObject("city");
                    ((MainActivity)getActivity()).getSupportActionBar().setTitle(jsonObjectCity.getString("name"));
                } catch (JSONException e) {
                    // Exibe mensagem de erro caso a conexao tenha problemas
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ForecastListFragment", e.getMessage());
                }

                // Cria a lista de forecasts atraves da resposta da API
                createForecastListFromJson(response);

                // Notifica o adapter que houve uma atualizacao na lista
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                // Exibe log de erro caso a conexao nao tenha sido completada
                VolleyLog.d("ForecastListFragment", "Error: " + e.getMessage());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adiciona a requisicao da fila do Volley
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }

    public void createForecastListFromJson(JSONObject response) {
        try {
            // Recupera o array
            JSONArray jsonArrayList = response.getJSONArray("list");

            // Itera sobre o array de forecasts
            for (int i = 0; i < jsonArrayList.length(); i++) {
                // Recupera as temperaturas
                JSONObject jsonObjectMain = jsonArrayList.getJSONObject(i).getJSONObject("temp");
                String tempMax = jsonObjectMain.getString("max").substring(0,2) + "º";
                String tempMin = jsonObjectMain.getString("min").substring(0,2) + "º";

                // Recupera o clima
                JSONArray jsonArrayWeather = jsonArrayList.getJSONObject(i).getJSONArray("weather");
                int weatherId = jsonArrayWeather.getJSONObject(0).getInt("id");

                // Recupera e converte o dia para string
                Time dayTime = new Time();
                dayTime.setToNow();
                int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
                long dateTime = dayTime.setJulianDay(julianStartDay+i);
                String day = getReadableDateString(dateTime);

                // Cria novo forecast
                Forecast forecast = new Forecast(tempMin, tempMax, weatherId, day);
                forecasts.add(forecast);
            }
        } catch (JSONException e) {
            // Exibe mensagem de erro caso a conexao tenha problemas
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ForecastListFragment", e.getMessage());
        }
    }

    private String getReadableDateString(long time){
        // Converte o long no string do formato abaixo (terça feira, 14)
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEEE',' dd");
        return shortenedDateFormat.format(time);
    }
}
