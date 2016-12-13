package com.rafaelguimas.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastDetailFragment extends Fragment {

    Forecast forecastSelected;

    public ForecastDetailFragment() {
        // Required empty public constructor
    }

    public static ForecastDetailFragment newInstance(Forecast forecastSelected) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();
        fragment.setForecastSelected(forecastSelected);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);

        // Busca os componentes da tela
        TextView txtMax = (TextView) view.findViewById(R.id.txt_max);
        TextView txtMin = (TextView) view.findViewById(R.id.txt_min);
        TextView txtDay = (TextView) view.findViewById(R.id.txt_day);
        ImageView imgWeather = (ImageView) view.findViewById(R.id.img_weather);

        // Exibe os textos na tela
        txtMax.setText(forecastSelected.getTempMax());
        txtMin.setText(forecastSelected.getTempMin());
        txtDay.setText(forecastSelected.getDay());

        // Define o icone de acordo com o codigo do forecast
        int weatherId = forecastSelected.getWeatherId();
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
        imgWeather.setImageResource(drawable);

        return view;
    }

    public void setForecastSelected(Forecast forecastSelected) {
        this.forecastSelected = forecastSelected;
    }

}
