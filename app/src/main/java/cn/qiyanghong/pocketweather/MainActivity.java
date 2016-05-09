package cn.qiyanghong.pocketweather;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.refresh.BeautifulRefreshLayout;

import java.util.Calendar;

import cn.qiyanghong.pocketweather.entity.*;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private static final int REQUEST_CITY = 1;
    BeautifulRefreshLayout refreshLayout;
    WeatherService weatherService;


    String prefixStr = null;

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
            tv_wind,        //风向风力
            tv_dressing_index,// 穿衣指数
            tv_dressing_advice,// 穿衣建议
            tv_uv_index,// 紫外线指数
            tv_comfort_index,// "",/*舒适度指数*/
            tv_wash_index,// "较适宜",	/*洗车指数*/
            tv_travel_index,// "适宜",	/*旅游指数*/
            tv_exercise_index,// "较适宜",	/*晨练指数*/
            tv_drying_index;// ""/*干燥指数*/

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
            iv_fifthday_weather,
            iv_sixthday_weather,
            iv_seventhday_weather;


    private RelativeLayout rl_city;//页头，点击可选择城市

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            weatherService = ((WeatherService.WeatherServiceBinder) service).getService();
            weatherService.registerCallback(weatherCallback);
            weatherService.refreshWeather();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            weatherService.unregisterCallback(weatherCallback);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Intent serviceIntent = new Intent(getApplicationContext(), WeatherService.class);
        bindService(serviceIntent, conn, BIND_AUTO_CREATE);
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

        tv_wind = (TextView) findViewById(R.id.tv_wind);
        tv_dressing_index = (TextView) findViewById(R.id.tv_dressing_index);
        tv_dressing_advice = (TextView) findViewById(R.id.tv_dressing_advice);
        tv_uv_index = (TextView) findViewById(R.id.tv_uv_index);
//        tv_comfort_index = (TextView) findViewById(R.id.tv_comfort_index);
        tv_wash_index = (TextView) findViewById(R.id.tv_wash_index);
        tv_travel_index = (TextView) findViewById(R.id.tv_travel_index);
        tv_exercise_index = (TextView) findViewById(R.id.tv_exercise_index);
//        tv_drying_index = (TextView) findViewById(R.id.tv_drying_index);

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
        iv_fifthday_weather = (ImageView) findViewById(R.id.iv_fifthday_weather);
        iv_sixthday_weather = (ImageView) findViewById(R.id.iv_sixthday_weather);
        iv_seventhday_weather = (ImageView) findViewById(R.id.iv_seventhday_weather);

        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        rl_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CityActivity.class));
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
                        weatherService.refreshWeather();
                    }
                });
            }
        });
    }

    OnWeatherCallback weatherCallback = new OnWeatherCallback() {
        @Override
        public void needRefreshCity() {
            new AlertDialog.Builder(MainActivity.this).setTitle("去选择你所在的城市")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(getApplicationContext(), CityActivity.class), REQUEST_CITY);
                        }
                    })
                    .show();
        }

        @Override
        public void onWeatherUpdated(WeatherBean weather) {
            Calendar c = Calendar.getInstance();
            int time = c.get(Calendar.HOUR_OF_DAY);
            prefixStr = null;
            if (time >= 6 && time < 18) {
                prefixStr = "d";
            } else {
                prefixStr = "n";
            }
            updateSK(weather.getSk());
            updateToday(weather.getToday());
            updateFuture(weather.getFutures());
        }

        @Override
        public void onForecastUpdated(Forecast[] forecasts) {
            //设置时间显示
            tv_next_three.setText(forecasts[0].getSh() + "点");
            tv_next_six.setText(forecasts[1].getSh() + "点");
            tv_next_nine.setText(forecasts[2].getSh() + "点");
            tv_next_twelve.setText(forecasts[3].getSh() + "点");
            tv_next_fifteen.setText(forecasts[4].getSh() + "点");

            //设置温度显示
            tv_next_three_temp.setText(forecasts[0].getTemp1() + "℃~" + forecasts[0].getTemp2() + "℃");
            tv_next_six_temp.setText(forecasts[1].getTemp1() + "℃~" + forecasts[1].getTemp2() + "℃");
            tv_next_nine_temp.setText(forecasts[2].getTemp1() + "℃~" + forecasts[2].getTemp2() + "℃");
            tv_next_twelve_temp.setText(forecasts[3].getTemp1() + "℃~" + forecasts[3].getTemp2() + "℃");
            tv_next_fifteen_temp.setText(forecasts[4].getTemp1() + "℃~" + forecasts[4].getTemp2() + "℃");

            //设置图标
            iv_next_three.setImageResource(getResources().getIdentifier(prefixStr + forecasts[0].getWeatherid(), "drawable", "cn.qiyanghong.pocketweather"));
            iv_next_six.setImageResource(getResources().getIdentifier(prefixStr + forecasts[1].getWeatherid(), "drawable", "cn.qiyanghong.pocketweather"));
            iv_next_nine.setImageResource(getResources().getIdentifier(prefixStr + forecasts[2].getWeatherid(), "drawable", "cn.qiyanghong.pocketweather"));
            iv_next_twelve.setImageResource(getResources().getIdentifier(prefixStr + forecasts[3].getWeatherid(), "drawable", "cn.qiyanghong.pocketweather"));
            iv_next_fifteen.setImageResource(getResources().getIdentifier(prefixStr + forecasts[4].getWeatherid(), "drawable", "cn.qiyanghong.pocketweather"));
        }

        @Override
        public void onPMUpdated(PM pm) {
            tv_aqi.setText(pm.getAQI());
            tv_quality.setText(pm.getQuality());
        }
    };

    private void updateSK(SK sk) {
        tv_release.setText(sk.getTime() + " 发布");
        tv_now_temp.setText(sk.getTemp() + " °");
    }

    private void updateToday(Today today) {
        tv_city.setText(today.getCity());

        tv_now_weather.setText(today.getWeather());

        Weather_Id weather_id = today.getWeather_id();
        int visibility;
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

        //详细信息
        tv_wind.setText(today.getWind());
        tv_dressing_index.setText(today.getDressing_index());
        tv_dressing_advice.setText(today.getDressing_advice());
        tv_uv_index.setText(today.getUv_index());
//        tv_comfort_index.setText(today.getComfort_index());
        tv_wash_index.setText(today.getWash_index());
        tv_travel_index.setText(today.getTravel_index());
        tv_exercise_index.setText(today.getExercise_index());
//        tv_drying_index.setText(today.getDrying_index());
    }

    private void updateFuture(Future[] futures) {
        tv_today_temp.setText(futures[0].getTemperature());
        tv_tommorrow_temp.setText(futures[1].getTemperature());
        tv_thirdday_temp.setText(futures[2].getTemperature());
        tv_fourthday_temp.setText(futures[3].getTemperature());
        tv_fifthday_temp.setText(futures[4].getTemperature());
        tv_sixthday_temp.setText(futures[5].getTemperature());
        tv_seventhday_temp.setText(futures[6].getTemperature());

        tv_tommorrow.setText(futures[1].getWeek());
        tv_thirdday.setText(futures[2].getWeek());
        tv_fourthday.setText(futures[3].getWeek());
        tv_fifthday.setText(futures[4].getWeek());
        tv_sixthday.setText(futures[5].getWeek());
        tv_seventhday.setText(futures[6].getWeek());

        String preFix = "d";
        iv_today_weather.setImageResource(getResources().getIdentifier(preFix + futures[0].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_tommorrow_weather.setImageResource(getResources().getIdentifier(preFix + futures[1].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_thirdday_weather.setImageResource(getResources().getIdentifier(preFix + futures[2].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_fourthday_weather.setImageResource(getResources().getIdentifier(preFix + futures[3].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_fifthday_weather.setImageResource(getResources().getIdentifier(preFix + futures[4].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_sixthday_weather.setImageResource(getResources().getIdentifier(preFix + futures[5].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
        iv_seventhday_weather.setImageResource(getResources().getIdentifier(preFix + futures[6].getWeather_id().getFa(), "drawable", "cn.qiyanghong.pocketweather"));
    }


    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CITY && resultCode == RESULT_OK) {
            weatherService.refreshWeather();
        }
    }

    interface OnWeatherCallback {
        void needRefreshCity();

        void onWeatherUpdated(WeatherBean weather);

        void onForecastUpdated(Forecast[] forecasts);

        void onPMUpdated(PM pm);
    }
}
