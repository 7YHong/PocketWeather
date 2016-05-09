package cn.qiyanghong.pocketweather;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import cn.qiyanghong.pocketweather.entity.City;
import cn.qiyanghong.pocketweather.entity.Forecast;
import cn.qiyanghong.pocketweather.entity.Future;
import cn.qiyanghong.pocketweather.entity.PM;
import cn.qiyanghong.pocketweather.entity.SK;
import cn.qiyanghong.pocketweather.entity.Today;
import cn.qiyanghong.pocketweather.entity.WeatherBean;

/**
 * Created by QYH on 2016/5/8.
 */
public class MyJsonParser {

    private static final java.lang.String TAG = MyJsonParser.class.getName();
    private static Gson gson=new Gson();

    /**
     * 与其它几个不同的是，这里把获得的结果保存到数据库
     * @param context
     * @param response
     * @throws Exception
     */
    public static void parseCity(Context context,String response) throws Exception{
        final Dao<City, Integer> cityDao = DBHelper.getHelper(context).getCityDao();

        JSONObject jsonObject = new JSONObject(response);
        String errercode = jsonObject.getString("error_code");
        if (!errercode.equals("0"))
            throw new RequestErrorException(jsonObject.getString("reason"));

        //然后解析数据
        JSONArray result = jsonObject.getJSONArray("result");
        final City[] cities = new Gson().fromJson(result.toString(), City[].class);
        cityDao.executeRawNoArgs("delete from city");//清空原有数据

        //优化的批量插入
        cityDao.callBatchTasks(new Callable<City>() {
            @Override
            public City call() throws Exception {
                for (City c : cities) {
                    cityDao.create(c);
                }
                return null;
            }
        });

        L.i(TAG,"->parseCity Success"+ String.valueOf(cityDao.countOf()));
    }

    public static WeatherBean parseWeatherBean(String response) throws Exception {
        JSONObject jsonObject = new JSONObject(response);
        String errercode = jsonObject.getString("error_code");
        if (!errercode.equals("0"))
            throw new RequestErrorException(jsonObject.getString("reason"));

        JSONObject result = jsonObject.getJSONObject("result");

        SK sk=gson.fromJson(result.getString("sk"),SK.class);
        Today today=gson.fromJson(result.getString("today"),Today.class);
        String future=result.getString("future");
        Future[] futures=gson.fromJson(future,Future[].class);
        return new WeatherBean(sk,today,futures);
    }

    public static Forecast[] parseForecast(String response)throws Exception{
        JSONObject jsonObject = new JSONObject(response);
        String errercode = jsonObject.getString("error_code");
        if (!errercode.equals("0"))
            throw new RequestErrorException(jsonObject.getString("reason"));


        JSONArray result = jsonObject.getJSONArray("result");
        Forecast[] forecasts=new Forecast[5];
        for (int i=0;i<5;i++){
            String forcast=result.getString(i+1);
            forecasts[i]=gson.fromJson(forcast,Forecast.class);
        }
        L.i(TAG, "->parseForecast Success!");
        return forecasts;
    }

    public static PM parsePM(String response) throws Exception{
        JSONObject jsonObject = new JSONObject(response);
        String errercode = jsonObject.getString("error_code");
        if (!errercode.equals("0"))
            throw new RequestErrorException(jsonObject.getString("reason"));


        JSONArray result = jsonObject.getJSONArray("result");
        PM pm = gson.fromJson(result.getString(0), PM.class);
        String pmValue = result.getJSONObject(0).getString("PM2.5");
        pm.setPM(pmValue);
        return pm;
    }
}
