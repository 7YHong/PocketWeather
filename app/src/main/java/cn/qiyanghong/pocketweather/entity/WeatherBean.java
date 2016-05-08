package cn.qiyanghong.pocketweather.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by QYH on 2016/5/8.
 */
public class WeatherBean {
    private SK sk;
    private Today today;
    private Future[] futures;

    public WeatherBean(SK sk, Today today, Future[] futures) {
        this.sk = sk;
        this.today = today;
        this.futures = futures;
    }

    public SK getSk() {
        return sk;
    }

    public void setSk(SK sk) {
        this.sk = sk;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public Future[] getFutures() {
        return futures;
    }

    public void setFutures(Future[] futures) {
        this.futures = futures;
    }
}
