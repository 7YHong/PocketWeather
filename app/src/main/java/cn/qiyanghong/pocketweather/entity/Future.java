package cn.qiyanghong.pocketweather.entity;

/**
 * Created by QYH on 2016/5/8.
 */
public class Future {
    String temperature,//": "28℃~36℃",
            weather;//": "晴转多云",
    Weather_Id weather_id;//":
    String wind,//": "南风3-4级",
            week,//": "星期一",
            date;//": "20140804"

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Weather_Id getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(Weather_Id weather_id) {
        this.weather_id = weather_id;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
