package cn.qiyanghong.pocketweather;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qiyanghong.pocketweather.entity.City;
import cn.qiyanghong.pocketweather.entity.Forecast;
import cn.qiyanghong.pocketweather.entity.PM;
import cn.qiyanghong.pocketweather.entity.WeatherBean;

public class WeatherService extends Service {
    private static final String TAG = WeatherService.class.getName();
    private static final String JSON_CITY = "{\"resultcode\":\"200\",\"reason\":\"successed\",\"result\":[{\"id\":\"1\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"北京\"},{\"id\":\"2\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"海淀\"},{\"id\":\"3\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"朝阳\"},{\"id\":\"4\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"顺义\"},{\"id\":\"5\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"怀柔\"},{\"id\":\"6\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"通州\"},{\"id\":\"7\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"昌平\"},{\"id\":\"8\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"延庆\"},{\"id\":\"9\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"丰台\"},{\"id\":\"10\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"石景山\"},{\"id\":\"11\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"大兴\"},{\"id\":\"12\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"房山\"},{\"id\":\"13\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"密云\"},{\"id\":\"14\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"门头沟\"},{\"id\":\"15\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"平谷\"},{\"id\":\"16\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"上海\"},{\"id\":\"17\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"闵行\"},{\"id\":\"18\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"宝山\"},{\"id\":\"19\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"嘉定\"},{\"id\":\"20\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"南汇\"},{\"id\":\"21\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"金山\"},{\"id\":\"22\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"青浦\"},{\"id\":\"23\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"松江\"},{\"id\":\"24\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"奉贤\"}],\"error_code\":0}";
    private static final String JSON_WEATHER = "{\"resultcode\":\"200\",\"reason\":\"successed!\",\"result\":{\"sk\":{\"temp\":\"16\",\"wind_direction\":\"北风\",\"wind_strength\":\"1级\",\"humidity\":\"83%\",\"time\":\"14:20\"},\"today\":{\"temperature\":\"14℃~19℃\",\"weather\":\"阵雨转小雨\",\"weather_id\":{\"fa\":\"03\",\"fb\":\"07\"},\"wind\":\"北风微风\",\"week\":\"星期日\",\"city\":\"长沙\",\"date_y\":\"2016年05月08日\",\"dressing_index\":\"较舒适\",\"dressing_advice\":\"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。\",\"uv_index\":\"最弱\",\"comfort_index\":\"\",\"wash_index\":\"不宜\",\"travel_index\":\"较不宜\",\"exercise_index\":\"较不宜\",\"drying_index\":\"\"},\"future\":[{\"temperature\":\"14℃~19℃\",\"weather\":\"阵雨转小雨\",\"weather_id\":{\"fa\":\"03\",\"fb\":\"07\"},\"wind\":\"北风微风\",\"week\":\"星期日\",\"date\":\"20160508\"},{\"temperature\":\"15℃~19℃\",\"weather\":\"中雨-大雨转阴\",\"weather_id\":{\"fa\":\"22\",\"fb\":\"02\"},\"wind\":\"北风微风\",\"week\":\"星期一\",\"date\":\"20160509\"},{\"temperature\":\"17℃~27℃\",\"weather\":\"多云转晴\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"00\"},\"wind\":\"东北风微风\",\"week\":\"星期二\",\"date\":\"20160510\"},{\"temperature\":\"21℃~30℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期三\",\"date\":\"20160511\"},{\"temperature\":\"20℃~31℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期四\",\"date\":\"20160512\"},{\"temperature\":\"15℃~19℃\",\"weather\":\"中雨-大雨转阴\",\"weather_id\":{\"fa\":\"22\",\"fb\":\"02\"},\"wind\":\"北风微风\",\"week\":\"星期五\",\"date\":\"20160513\"},{\"temperature\":\"21℃~30℃\",\"weather\":\"晴\",\"weather_id\":{\"fa\":\"00\",\"fb\":\"00\"},\"wind\":\"南风微风\",\"week\":\"星期六\",\"date\":\"20160514\"}]},\"error_code\":0}";
    private static final String JSON_FORECAST = "{\"resultcode\":\"200\",\"reason\":\"successed!\",\"result\":[{\"weatherid\":\"01\",\"weather\":\"多云\",\"temp1\":\"18\",\"temp2\":\"17\",\"sh\":\"17\",\"eh\":\"20\",\"date\":\"20160508\",\"sfdate\":\"20160508170000\",\"efdate\":\"20160508200000\"},{\"weatherid\":\"01\",\"weather\":\"多云\",\"temp1\":\"17\",\"temp2\":\"17\",\"sh\":\"20\",\"eh\":\"23\",\"date\":\"20160508\",\"sfdate\":\"20160508200000\",\"efdate\":\"20160508230000\"},{\"weatherid\":\"03\",\"weather\":\"阵雨\",\"temp1\":\"17\",\"temp2\":\"17\",\"sh\":\"23\",\"eh\":\"02\",\"date\":\"20160508\",\"sfdate\":\"20160508230000\",\"efdate\":\"20160509020000\"},{\"weatherid\":\"03\",\"weather\":\"阵雨\",\"temp1\":\"17\",\"temp2\":\"18\",\"sh\":\"02\",\"eh\":\"05\",\"date\":\"20160509\",\"sfdate\":\"20160509020000\",\"efdate\":\"20160509050000\"},{\"weatherid\":\"09\",\"weather\":\"大雨\",\"temp1\":\"18\",\"temp2\":\"18\",\"sh\":\"05\",\"eh\":\"08\",\"date\":\"20160509\",\"sfdate\":\"20160509050000\",\"efdate\":\"20160509080000\"},{\"weatherid\":\"09\",\"weather\":\"大雨\",\"temp1\":\"18\",\"temp2\":\"18\",\"sh\":\"08\",\"eh\":\"11\",\"date\":\"20160509\",\"sfdate\":\"20160509080000\",\"efdate\":\"20160509110000\"},{\"weatherid\":\"07\",\"weather\":\"小雨\",\"temp1\":\"18\",\"temp2\":\"15\",\"sh\":\"11\",\"eh\":\"14\",\"date\":\"20160509\",\"sfdate\":\"20160509110000\",\"efdate\":\"20160509140000\"},{\"weatherid\":\"07\",\"weather\":\"小雨\",\"temp1\":\"15\",\"temp2\":\"15\",\"sh\":\"14\",\"eh\":\"17\",\"date\":\"20160509\",\"sfdate\":\"20160509140000\",\"efdate\":\"20160509170000\"},{\"weatherid\":\"01\",\"weather\":\"多云\",\"temp1\":\"15\",\"temp2\":\"15\",\"sh\":\"17\",\"eh\":\"20\",\"date\":\"20160509\",\"sfdate\":\"20160509170000\",\"efdate\":\"20160509200000\"},{\"weatherid\":\"01\",\"weather\":\"多云\",\"temp1\":\"15\",\"temp2\":\"15\",\"sh\":\"20\",\"eh\":\"23\",\"date\":\"20160509\",\"sfdate\":\"20160509200000\",\"efdate\":\"20160509230000\"}],\"error_code\":0}";
    private static final String JSON_PM = "{\"resultcode\":\"200\",\"reason\":\"SUCCESSED!\",\"result\":[{\"city\":\"长沙\",\"PM2.5\":\"60\",\"AQI\":\"85\",\"quality\":\"良\",\"PM10\":\"101\",\"CO\":\"0.7\",\"NO2\":\"23\",\"O3\":\"71\",\"SO2\":\"8\",\"time\":\"2016-05-08 19:33:48\"}],\"error_code\":0}";

    public static final int REQ_TYPE_CITY = 0;
    public static final int REQ_TYPE_WEATHER = 1;
    public static final int REQ_TYPE_FORECAST = 2;
    public static final int REQ_TYPE_PM = 3;

    private static String CITY_NAME = null;
    private static int CITY_ID;

    private static boolean isNeedRefreshCity = true;


    WeatherServiceBinder mBinder = new WeatherServiceBinder();
    Gson gson;
    MainActivity.OnWeatherCallback weatherCallback;
    CityActivity.OnCityCallback cityCallback;

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

//        parseWeather(JSON_WEATHER);
//        parseForecast(JSON_FORECAST);
//        parsePM(JSON_PM);
        refreshWeather();

        return super.onStartCommand(intent, flags, startId);
    }

    public void refreshWeather() {
        if (SPUtils.contains(getApplicationContext(), "cityname")) {
            CITY_NAME = (String) SPUtils.get(getApplicationContext(), "cityname", "");
            CITY_ID = (int) SPUtils.get(getApplicationContext(), "cityid", 0);
            isNeedRefreshCity = false;
            L.i(TAG, "->requestDataFromCloud\tcityname:" + CITY_NAME);
            L.i(TAG, "->requestDataFromCloud\tcityid:" + CITY_ID);
        }

        if (isNeedRefreshCity) {
            L.i(TAG, "->requestDataFromCloud\tisNeedRefreshCity:" + isNeedRefreshCity);
            weatherCallback.needRefreshCity();
            return;
        }

        requestDataFromCloud(REQ_TYPE_WEATHER);
        requestDataFromCloud(REQ_TYPE_FORECAST);
        requestDataFromCloud(REQ_TYPE_PM);
    }

    /**
     * 从服务器获取数据
     */
    public void refreshCity() {
        requestDataFromCloud(REQ_TYPE_CITY);
    }

    public void searchCity(String cityname) {
        try {
            List<City> result = null;
            final Dao<City, Integer> cityDao = DBHelper.getHelper(getApplicationContext()).getCityDao();
            result = cityDao.queryForEq("district", cityname);
            if (result.size() == 0) {
                result = cityDao.queryForEq("city", cityname);
            }
            cityCallback.onSearchDone(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照省名整理好之后，回调交给前台显示
     * 不会从网络请求数据
     *
     * @throws Exception
     */
    public void getCities() throws Exception {
        Dao<City, Integer> cityDao = DBHelper.getHelper(getApplicationContext()).getCityDao();
        List<City> provinceGroup = cityDao.queryBuilder().selectColumns("province").distinct().query();//只有城市名，数据不全

        final int size = provinceGroup.size();
        final String[] provinces = new String[size];      //列表大小跟cities是一样的
        final List<List<City>> cities = new ArrayList<>();
        //获得省级目录
        for (int i = 0; i < provinceGroup.size(); i++) {       //根据省名填充cities
            String province = provinceGroup.get(i).getProvince();
            provinces[i] = province;
            L.i(TAG, "province:" + province);
            //按照省名查所有的城市
            List<City> city = cityDao.queryForEq("province", province);
            cities.add(city);
            L.i(TAG, "descript:" + city.get(0).toString());
        }
        if (cityCallback != null)
            cityCallback.onCityUpdate(provinces, cities);
    }

    private void parseCitys(String response) {
        try {
            MyJsonParser.parseCity(getApplication(), response);

            getCities();
        } catch (RequestErrorException e) {
            e.printStackTrace();
            showToast("刷新失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showToast("刷新失败！");
        }
    }


    private void parseWeather(String response) {
        try {
            WeatherBean weather = MyJsonParser.parseWeatherBean(response);
            if (weatherCallback != null)
                weatherCallback.onWeatherUpdated(weather);
            L.i(TAG, "->parseWeather Success!");
        } catch (RequestErrorException e) {
            e.printStackTrace();
            showToast("刷新失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showToast("刷新失败！");
        }
    }

    private void parseForecast(String response) {
        try {
            Forecast[] forecasts = MyJsonParser.parseForecast(response);
            if (weatherCallback != null)
                weatherCallback.onForecastUpdated(forecasts);
        } catch (RequestErrorException e) {
            e.printStackTrace();
            showToast("刷新失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showToast("刷新失败！");
        }
    }

    private void parsePM(String response) {
        try {
            PM pm = MyJsonParser.parsePM(response);
            if (weatherCallback != null) {
                weatherCallback.onPMUpdated(pm);
            }
        } catch (RequestErrorException e) {
            e.printStackTrace();
            showToast("刷新失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showToast("刷新失败！");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void requestDataFromCloud(int req_type) {

//        if (SPUtils.contains(getApplicationContext(), "cityname")) {
//            CITY_NAME = (String) SPUtils.get(getApplicationContext(), "cityname", "");
//            CITY_ID = (int) SPUtils.get(getApplicationContext(), "cityid", 0);
//            isNeedRefreshCity = false;
//            L.i(TAG,"->requestDataFromCloud\tcityname:"+CITY_NAME);
//            L.i(TAG,"->requestDataFromCloud\tcityid:"+CITY_ID);
//        }
//
//        if (req_type != REQ_TYPE_CITY) {
//            if (isNeedRefreshCity) {
//                L.i(TAG, "->requestDataFromCloud\tisNeedRefreshCity:" + isNeedRefreshCity);
//                weatherCallback.needRefreshCity();
//                return;
//            }
//        }

        Parameters params = new Parameters();
        String url = "";
        int did = 39;
        switch (req_type) {
            case REQ_TYPE_CITY:
                url = "http://v.juhe.cn/weather/citys";
                did = 39;
                break;
            case REQ_TYPE_WEATHER:
                did = 39;
                url = "http://v.juhe.cn/weather/index";
                params.add("cityname", CITY_ID);
                params.add("format", 2);
                break;
            case REQ_TYPE_FORECAST:
                did = 39;
                url = "http://v.juhe.cn/weather/forecast3h";
                params.add("cityname", CITY_ID);
                break;
            case REQ_TYPE_PM:
                params.add("city", CITY_NAME);
                did = 33;
                url = "http://web.juhe.cn:8080/environment/air/pm";
                break;
            default:
                return;
        }
        JuheData.executeWithAPI(getApplicationContext(), did, url, JuheData.GET, params
                , new MyRequestCallback(req_type));
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG, "->onBind");
        return mBinder;
    }

//    public void parseWeather(){
//        L.i(TAG,"->parseWeather");
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

    public void unregisterCallback(MainActivity.OnWeatherCallback WeatherCallback) {
        this.weatherCallback = null;
    }

    public void registerCallback(CityActivity.OnCityCallback cityCallback) {
        this.cityCallback = cityCallback;
    }

    public void unregisterCallback(CityActivity.OnCityCallback cityCallback) {
        this.cityCallback = null;
    }


    class MyRequestCallback implements DataCallBack {
        int req_type;

        public MyRequestCallback(int req_type) {
            this.req_type = req_type;
        }

        @Override
        public void onSuccess(int i, String s) {
            L.i(TAG, "HttpCode:" + i + ",Result:\n" + s);
            switch (req_type) {
                case REQ_TYPE_CITY:
                    parseCitys(s);
                    break;
                case REQ_TYPE_WEATHER:
                    parseWeather(s);
                    break;
                case REQ_TYPE_FORECAST:
                    parseForecast(s);
                    break;
                case REQ_TYPE_PM:
                    parsePM(s);
                    break;
            }

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onFailure(int i, String s, Throwable throwable) {
            showToast("刷新失败！");
        }
    }
}
