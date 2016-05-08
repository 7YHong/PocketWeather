package cn.qiyanghong.pocketweather.entity;

/**
 * Created by QYH on 2016/5/8.
 * 实况
 */
public class SK {
    String temp,//":"21",	/*当前温度*/
           wind_direction,//":"西风",	/*当前风向*/
           wind_strength,//":"2级",	/*当前风力*/
           humidity,//":"4%",	/*当前湿度*/
           time;//":"14:25"	/*更新时间*/

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_strength() {
        return wind_strength;
    }

    public void setWind_strength(String wind_strength) {
        this.wind_strength = wind_strength;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
