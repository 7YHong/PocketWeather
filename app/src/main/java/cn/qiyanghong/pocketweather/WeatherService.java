package cn.qiyanghong.pocketweather;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.google.gson.Gson;

import org.json.JSONObject;

import cn.qiyanghong.pocketweather.entity.WeatherBean;

public class WeatherService extends Service {
    private static final String TAG = WeatherService.class.getName();

    WeatherServiceBinder mBinder = new WeatherServiceBinder();
    Gson gson;
    MainActivity.OnWeatherCallback weatherCallback;

    class WeatherServiceBinder extends Binder {
        public WeatherService getService() {
            return WeatherService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson = new Gson();
        L.i(TAG, "->onCreate");
    }

    @Override
    public void onDestroy() {
        weatherCallback = null;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i(TAG, "->onStartCommand");
        if (weatherCallback == null) return super.onStartCommand(intent, flags, startId);

        getWeather();

        return super.onStartCommand(intent, flags, startId);
    }

    public void getWeather() {
        String s = "{\"resultcode\":\"200\",\"reason\":\"successed!\",\"result\":{\"sk\":{\"temp\":\"16\",\"wind_direction\":\"北风\",\"wind_strength\":\"1级\",\"humidity\":\"83%\",\"time\":\"14:20\"},\"today\":{\"temperature\":\"14℃~19℃\",\"weather\":\"阵雨转小雨\",\"weather_id\":{\"fa\":\"03\",\"fb\":\"07\"},\"wind\":\"北风微风\",\"week\":\"星期日\",\"city\":\"长沙\",\"date_y\":\"2016年05月08日\",\"dressing_index\":\"较舒适\",\"dressing_advice\":\"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。\",\"uv_index\":\"最弱\",\"comfort_index\":\"\",\"wash_index\":\"不宜\",\"travel_index\":\"较不宜\",\"exercise_index\":\"较不宜\",\"drying_index\":\"\"},\"future\":[{\"temperature\":\"14℃~19℃\",\"weather\":\"阵雨转小雨\",\"weather_id\":{\"fa\":\"03\",\"fb\":\"07\"},\"wind\":\"北风微风\",\"week\":\"星期日\",\"date\":\"20160508\"},{\"temperature\":\"15℃~19℃\",\"weather\":\"中雨-大雨转阴\",\"weather_id\":{\"fa\":\"22\",\"fb\":\"02\"},\"wind\":\"北风微风\",\"week\":\"星期一\",\"date\":\"20160509\"},{\"temperature\":\"17℃~27℃\",\"weather\":\"多云转晴\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"00\"},\"wind\":\"东北风微风\",\"week\":\"星期二\",\"date\":\"20160510\"},{\"temperature\":\"21℃~30℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期三\",\"date\":\"20160511\"},{\"temperature\":\"20℃~31℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期四\",\"date\":\"20160512\"},{\"temperature\":\"15℃~19℃\",\"weather\":\"中雨-大雨转阴\",\"weather_id\":{\"fa\":\"22\",\"fb\":\"02\"},\"wind\":\"北风微风\",\"week\":\"星期五\",\"date\":\"20160513\"},{\"temperature\":\"21℃~30℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期六\",\"date\":\"20160514\"}]},\"error_code\":0}" ;
        try {
            JSONObject jsonobject = new JSONObject(s);
            String errercode = jsonobject.getString("error_code");
            if (!errercode.equals("0")) return;

            JSONObject resu=jsonobject.getJSONObject("result");

            WeatherBean weather=MyJsonParser.parseFromJson(resu);

            weatherCallback.onWeatherUpdated(weather);
            L.i(TAG, "request Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG, "->onBind");
        return mBinder;
    }

//    public void getWeather(){
//        L.i(TAG,"->getWeather");
//        Parameters params=new Parameters();
//        params.add("cityname","长沙");
//        params.add("format","2");
//        JuheData.executeWithAPI(getApplicationContext()
//                , 39
//                , "http://v.juhe.cn/weather/index"
//                , JuheData.GET
//                , params
//                , new DataCallBack() {
//                    @Override
//                    public void onSuccess(int i, String s) {
//                        L.i(TAG,"HttpCode:"+i+",Result:\n"+s);
//                        if (i!=200) return;
//                        try {
//                            JSONObject jsonobject=new JSONObject(s);
//                            String errercode=jsonobject.getString("error_code");
//                            if (!errercode.equals("0")) return;
//                            String result=jsonobject.getString("result");
//                            WeatherBean weatherResult=gson.fromJson(result,WeatherBean.class);
////                            JSONObject result=jsonobject.getJSONObject("result");
////                            String sk=result.getString("sk");
////                            L.i(TAG,"sk:"+sk);
////                            SK sko=gson.fromJson(sk,SK.class);
////                            String today=result.getString("today");
////                            L.i(TAG,"today:"+today);
////                            Today todayo=gson.fromJson(today,Today.class);
////                            String future=result.getString("future");
////                            L.i(TAG,"future:"+future);
////                            Future[] futures=gson.fromJson(future,new TypeToken< Future[]>(){}.getType());
//
//
//                            L.i(TAG,"request Success!");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s, Throwable throwable) {
//                        L.i(TAG,"HttpCode:"+i+",Result:\n"+s);
//                    }
//                });
//    }

    public void registerCallback(MainActivity.OnWeatherCallback WeatherCallback) {
        this.weatherCallback = WeatherCallback;
    }
}
