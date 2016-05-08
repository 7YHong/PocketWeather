package cn.qiyanghong.pocketweather;

import com.google.gson.Gson;

import org.json.JSONObject;

import cn.qiyanghong.pocketweather.entity.Future;
import cn.qiyanghong.pocketweather.entity.SK;
import cn.qiyanghong.pocketweather.entity.Today;
import cn.qiyanghong.pocketweather.entity.WeatherBean;

/**
 * Created by QYH on 2016/5/8.
 */
public class MyJsonParser {

    private static Gson gson=new Gson();

    public static WeatherBean parseFromJson(JSONObject result) throws Exception {
        SK sk=gson.fromJson(result.getString("sk"),SK.class);
        Today today=gson.fromJson(result.getString("today"),Today.class);
        String future=result.getString("future");
        Future[] futures=gson.fromJson(future,Future[].class);
        return new WeatherBean(sk,today,futures);
    }
}
