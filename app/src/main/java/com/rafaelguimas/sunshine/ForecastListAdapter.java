package com.rafaelguimas.sunshine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Forecast> forecastList;
    private boolean first = true;

    public ForecastListAdapter(Context context, ArrayList<Forecast> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Verifica qual layout deve ser escolhido
        int layout;
        if (first) {
            // Layout do primeiro item (cabecalho)
            layout = R.layout.item_list_forecast_header;
            first = false;
        } else {
            // Layout dos demais itens
            layout = R.layout.item_list_forecast;
        }

        // Infla o layout correto
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Recupera os valores da lista na posicao atual
        String tempMax = forecastList.get(position).getTempMax();
        String tempMin = forecastList.get(position).getTempMin();
        String day = forecastList.get(position).getDay();
        int weatherId = forecastList.get(position).getWeatherId();

        // Define os valores no item
        holder.txtMax.setText(tempMax);
        holder.txtMin.setText(tempMin);

        // Define o titulo de acordo com a posicao do item
        if (position == 0) {
            holder.txtDay.setText(context.getString(R.string.today) + " " + day);
        } else {
            holder.txtDay.setText(day);
        }

        // Define o icone de acordo com o codigo recebido
        int drawable;
        if (weatherId >= 200 || weatherId <= 230) {
            // Tempestade
            drawable = R.drawable.storm;
        } else if (weatherId >= 300 || weatherId <= 321) {
            // Chuvisco
            drawable = R.drawable.hail;
        } else if (weatherId >= 500 || weatherId <= 531) {
            // Chuva
            drawable = R.drawable.rainy;
        } else if (weatherId >= 600 || weatherId <= 622) {
            // Neve
            drawable = R.drawable.snowflake;
        } else if (weatherId >= 801 || weatherId <= 804) {
            // Nublado
            drawable = R.drawable.cloudy_1;
        } else {
            // Ensolarado
            drawable = R.drawable.sun;
        }
        holder.imgWeather.setImageResource(drawable);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chama o controlador de fragment
                ((MainActivity) context).getSupportFragmentManager()
                        // Inicia a transacao
                        .beginTransaction()
                        // Troca o fragmento do container para o fragment de detalhes
                        .replace(R.id.container, ForecastDetailFragment.newInstance(forecastList.get(position)))
                        // Permite que volte para o fragment anterior
                        .addToBackStack(null)
                        // Confirma a operacao
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    /**
     * Objeto necessario para mapear os componentes do layout do item da lista
     * */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView txtMax, txtMin, txtDay;
        private ImageView imgWeather;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.txtMax = (TextView) view.findViewById(R.id.txt_max);
            this.txtMin = (TextView) view.findViewById(R.id.txt_min);
            this.txtDay = (TextView) view.findViewById(R.id.txt_day);
            this.imgWeather = (ImageView) view.findViewById(R.id.img_weather);
        }
    }
}
