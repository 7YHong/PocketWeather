package cn.qiyanghong.pocketweather;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.qiyanghong.pocketweather.entity.Forecast;
import cn.qiyanghong.pocketweather.entity.Future;
import cn.qiyanghong.pocketweather.entity.SK;
import cn.qiyanghong.pocketweather.entity.Today;
import cn.qiyanghong.pocketweather.entity.WeatherBean;

/**
 * Created by QYH on 2016/5/8.
 */
public class MyJsonParser {

    private static final java.lang.String TAG = MyJsonParser.class.getName();
    private static Gson gson=new Gson();

    public static WeatherBean parseWeatherBean(JSONObject result) throws JSONException {
        SK sk=gson.fromJson(result.getString("sk"),SK.class);
        Today today=gson.fromJson(result.getString("today"),Today.class);
        String future=result.getString("future");
        Future[] futures=gson.fromJson(future,Future[].class);
        return new WeatherBean(sk,today,futures);
    }

    public static Forecast[] parseForecast(JSONArray result)throws Exception{
        Forecast[] forecasts=new Forecast[5];
        for (int i=0;i<5;i++){
            String forcast=result.getString(i+1);
//            L.i(TAG,forcast);
            forecasts[i]=gson.fromJson(forcast,Forecast.class);
        }
        return forecasts;
    }
}
