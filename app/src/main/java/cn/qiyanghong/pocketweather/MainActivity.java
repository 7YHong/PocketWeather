package cn.qiyanghong.pocketweather;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.refresh.BeautifulRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import cn.qiyanghong.pocketweather.entity.*;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private static final int RESULT_CITY = 1;
    BeautifulRefreshLayout refreshLayout;
    WeatherService weatherService;

    private TextView tv_city,// 城市
            tv_release,// 发布时间
            tv_now_weather,// 天气
            tv_today_temprature,// 温度
            tv_now_temp,// 当前温度
            tv_aqi,// 空气质量指数
            tv_quality,// 空气质量
            tv_next_three,// 3小时
            tv_next_six,// 6小时
            tv_next_nine,// 9小时
            tv_next_twelve,// 12小时
            tv_next_fifteen,// 15小时
            tv_next_three_temp,// 3小时温度
            tv_next_six_temp,// 6小时温度
            tv_next_nine_temp,// 9小时温度
            tv_next_twelve_temp,// 12小时温度
            tv_next_fifteen_temp,// 15小时温度
            tv_today_temp,// 今天温度a
            tv_tommorrow,// 明天
            tv_tommorrow_temp,// 明天温度a
            tv_thirdday,// 第三天
            tv_thirdday_temp,// 第三天温度a
            tv_fourthday,// 第四天
            tv_fourthday_temp,// 第四天温度a
            tv_fifthday,       // 第五天
            tv_fifthday_temp,// 第五天温度a
            tv_sixthday,       // 第六天
            tv_sixthday_temp,// 第六天温度a
            tv_seventhday,       // 第七天
            tv_seventhday_temp,// 第七天温度a
            tv_humidity,// 湿度
            tv_wind, tv_uv_index,// 紫外线指数
            tv_dressing_index;// 穿衣指数

    private ImageView iv_now_weather_a;// 现在a
    private View iv_now_weather_divider;// 分隔线
    private ImageView iv_now_weather_b,// 现在b
            iv_next_three,// 3小时
            iv_next_six,// 6小时
            iv_next_nine,// 9小时
            iv_next_twelve,// 12小时
            iv_next_fifteen,// 15小时
            iv_today_weather,// 今天
            iv_tommorrow_weather,// 明天
            iv_thirdday_weather,// 第三天
            iv_fourthday_weather,// 第四天
            iv_fifthday_weather,// 第四天
            iv_sixthday_weather,// 第四天
            iv_seventhday_weather;// 第四天


    private RelativeLayout rl_city;//页头，点击可选择城市

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            weatherService = ((WeatherService.WeatherServiceBinder) service).getService();
            weatherService.registerCallback(weatherCallback);
            weatherService.getWeather();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
//        testFun();
        Intent serviceIntent = new Intent(getApplicationContext(), WeatherService.class);
        bindService(serviceIntent, conn, BIND_AUTO_CREATE);
    }

    private void testFun() {
        Gson gson = new Gson();
        String json = "{\n" +
                "\" +\n" +
                "                    \"    \\\"resultcode\\\": \\\"200\\\",\\n\" +\n" +
                "                    \"    \\\"reason\\\": \\\"SUCCESSED!\\\",\\n\" +\n" +
                "                    \"    \\\"result\\\": [\\n\" +\n" +
                "                    \"        {\\n\" +\n" +
                "                    \"            \\\"city\\\": \\\"苏州\\\",\\n\" +\n" +
                "                    \"            \\\"PM2.5\\\": \\\"73\\\",\\n\" +\n" +
                "                    \"            \\\"AQI\\\": \\\"98\\\",\\n\" +\n" +
                "                    \"            \\\"quality\\\": \\\"良\\\",\\n\" +\n" +
                "                    \"            \\\"PM10\\\": \\\"50\\\",\\n\" +\n" +
                "                    \"            \\\"CO\\\": \\\"0.79\\\",\\n\" +\n" +
                "                    \"            \\\"NO2\\\": \\\"65\\\",\\n\" +\n" +
                "                    \"            \\\"O3\\\": \\\"28\\\",\\n\" +\n" +
                "                    \"            \\\"SO2\\\": \\\"41\\\",\\n\" +\n" +
                "                    \"            \\\"time\\\": \\\"2014-12-26 11:48:40\\\"\\n\" +\n" +
                "                    \"        }\\n\" +\n" +
                "                    \"    ],\\n\" +\n" +
                "                    \"    \\\"error_code\\\": 0\\n\" +\n" +
                "                    \"}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            PM pm = gson.fromJson(result.getString(0), PM.class);
            L.i(TAG, "AQI:" + pm.getAQI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_release = (TextView) findViewById(R.id.tv_release);
        tv_now_weather = (TextView) findViewById(R.id.tv_now_weather);
        tv_today_temprature = (TextView) findViewById(R.id.tv_today_temprature);
        tv_now_temp = (TextView) findViewById(R.id.tv_now_temp);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_quality = (TextView) findViewById(R.id.tv_quality);
        tv_next_three = (TextView) findViewById(R.id.tv_next_three);
        tv_next_six = (TextView) findViewById(R.id.tv_next_six);
        tv_next_nine = (TextView) findViewById(R.id.tv_next_nine);
        tv_next_twelve = (TextView) findViewById(R.id.tv_next_twelve);
        tv_next_fifteen = (TextView) findViewById(R.id.tv_next_fifteen);
        tv_next_three_temp = (TextView) findViewById(R.id.tv_next_three_temp);
        tv_next_six_temp = (TextView) findViewById(R.id.tv_next_six_temp);
        tv_next_nine_temp = (TextView) findViewById(R.id.tv_next_nine_temp);
        tv_next_twelve_temp = (TextView) findViewById(R.id.tv_next_twelve_temp);
        tv_next_fifteen_temp = (TextView) findViewById(R.id.tv_next_fifteen_temp);
        tv_today_temp = (TextView) findViewById(R.id.tv_today_temp);
        tv_tommorrow = (TextView) findViewById(R.id.tv_tommorrow);
        tv_tommorrow_temp = (TextView) findViewById(R.id.tv_tommorrow_temp);
        tv_thirdday = (TextView) findViewById(R.id.tv_thirdday);
        tv_thirdday_temp = (TextView) findViewById(R.id.tv_thirdday_temp);

        tv_fourthday = (TextView) findViewById(R.id.tv_fourthday);
        tv_fourthday_temp = (TextView) findViewById(R.id.tv_fourthday_temp);

        tv_fifthday = (TextView) findViewById(R.id.tv_fifthday);
        tv_fifthday_temp = (TextView) findViewById(R.id.tv_fifthday_temp);

        tv_sixthday = (TextView) findViewById(R.id.tv_sixthday);
        tv_sixthday_temp = (TextView) findViewById(R.id.tv_sixthday_temp);

        tv_seventhday = (TextView) findViewById(R.id.tv_seventhday);
        tv_seventhday_temp = (TextView) findViewById(R.id.tv_seventhday_temp);

        tv_humidity = (TextView) findViewById(R.id.tv_humidity);
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        tv_uv_index = (TextView) findViewById(R.id.tv_uv_index);
        tv_dressing_index = (TextView) findViewById(R.id.tv_dressing_index);

        iv_now_weather_a = (ImageView) findViewById(R.id.iv_now_weather_a);
        iv_now_weather_divider = findViewById(R.id.iv_now_weather_divider);
        iv_now_weather_b = (ImageView) findViewById(R.id.iv_now_weather_b);
        iv_next_three = (ImageView) findViewById(R.id.iv_next_three);
        iv_next_six = (ImageView) findViewById(R.id.iv_next_six);
        iv_next_nine = (ImageView) findViewById(R.id.iv_next_nine);
        iv_next_twelve = (ImageView) findViewById(R.id.iv_next_twelve);
        iv_next_fifteen = (ImageView) findViewById(R.id.iv_next_fifteen);
        iv_today_weather = (ImageView) findViewById(R.id.iv_today_weather);
        iv_tommorrow_weather = (ImageView) findViewById(R.id.iv_tommorrow_weather);
        iv_thirdday_weather = (ImageView) findViewById(R.id.iv_thirdday_weather);
        iv_fourthday_weather = (ImageView) findViewById(R.id.iv_fourthday_weather);

        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        rl_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CityActivity.class), RESULT_CITY);
            }
        });

        refreshLayout = (BeautifulRefreshLayout) findViewById(R.id.refreshlayout);
        refreshLayout.setBuautifulRefreshListener(new BeautifulRefreshLayout.BuautifulRefreshListener() {
            @Override
            public void onRefresh(final BeautifulRefreshLayout refreshLayout) {
                refreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                        weatherService.getWeather();
                    }
                });
            }
        });
    }

    OnWeatherCallback weatherCallback = new OnWeatherCallback() {
        @Override
        public void onWeatherUpdated(WeatherBean weather) {
            updateSK(weather.getSk());
            updateToday(weather.getToday());
            updateFuture(weather.getFutures());
        }

        @Override
        public void onPMUpdated(PM pm) {
            updatePM(pm);
        }
    };

    private void updateSK(SK sk) {
        tv_release.setText(sk.getTime() + " 发布");
        tv_now_temp.setText(sk.getTemp() + " °");
    }

    private void updateToday(Today today) {
        tv_city.setText(today.getCity());

        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);
        String prefixStr = null;
        if (time >= 6 && time < 18) {
            prefixStr = "d";
        } else {
            prefixStr = "n";
        }
        tv_now_weather.setText(today.getWeather());

        Weather_Id weather_id = today.getWeather_id();
        int visibility = View.VISIBLE;
        if (weather_id.isSingle()) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }
        iv_now_weather_divider.setVisibility(visibility);
        iv_now_weather_b.setVisibility(visibility);

        iv_now_weather_a.setImageResource(getResources().getIdentifier(prefixStr + weather_id.getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_now_weather_b.setImageResource(getResources().getIdentifier(prefixStr + weather_id.getFb(), "drawable", "cn.qiyanghong.pocketweather"));

        // 温度 8℃~16℃" ↑ ↓ °
        tv_today_temprature.setText(today.getTemperature());
    }

    private void updateFuture(Future[] futures) {
        tv_today_temp.setText (futures[0].getTemperature());
        tv_tommorrow_temp.setText (futures[1].getTemperature());
        tv_thirdday_temp.setText (futures[2].getTemperature());
        tv_fourthday_temp.setText (futures[3].getTemperature());
        tv_fifthday_temp.setText (futures[4].getTemperature());
        tv_sixthday_temp.setText (futures[5].getTemperature());
        tv_seventhday_temp.setText (futures[6].getTemperature());

        tv_tommorrow.setText (futures[1].getWeek());
        tv_thirdday.setText (futures[2].getWeek());
        tv_fourthday.setText (futures[3].getWeek());
        tv_fifthday.setText (futures[4].getWeek());
        tv_sixthday.setText (futures[5].getWeek());
        tv_seventhday.setText (futures[6].getWeek());
        //L.i(TAG,futures[6].getWeek());
    }

    private void updatePM(PM pm) {

    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    interface OnWeatherCallback {
        void onWeatherUpdated(WeatherBean weather);

        void onPMUpdated(PM pm);
    }
}
