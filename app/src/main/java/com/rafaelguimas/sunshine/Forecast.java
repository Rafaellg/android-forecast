package com.rafaelguimas.sunshine;

/**
 * Created by Rafael on 12/12/2016.
 */

public class Forecast {
    private String tempMin;
    private String tempMax;
    private int weatherId;
    private String day;

    public Forecast(String tempMin, String tempMax, int weatherId, String day) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.weatherId = weatherId;
        this.day = day;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
