package cn.qiyanghong.pocketweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.qiyanghong.pocketweather.entity.City;

public class CityActivity extends AppCompatActivity {
    private static final String JSON = "[{\"id\":\"1\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"北京\"},{\"id\":\"2\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"海淀\"},{\"id\":\"3\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"朝阳\"},{\"id\":\"4\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"顺义\"},{\"id\":\"5\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"怀柔\"},{\"id\":\"6\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"通州\"},{\"id\":\"7\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"昌平\"},{\"id\":\"8\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"延庆\"},{\"id\":\"9\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"丰台\"},{\"id\":\"10\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"石景山\"},{\"id\":\"11\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"大兴\"},{\"id\":\"12\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"房山\"},{\"id\":\"13\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"密云\"},{\"id\":\"14\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"门头沟\"},{\"id\":\"15\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"平谷\"},{\"id\":\"16\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"上海\"},{\"id\":\"17\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"闵行\"},{\"id\":\"18\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"宝山\"},{\"id\":\"19\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"嘉定\"},{\"id\":\"20\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"南汇\"},{\"id\":\"21\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"金山\"},{\"id\":\"22\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"青浦\"},{\"id\":\"23\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"松江\"},{\"id\":\"24\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"奉贤\"},{\"id\":\"25\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"崇明\"},{\"id\":\"26\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"徐家汇\"},{\"id\":\"27\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"浦东\"},{\"id\":\"28\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"天津\"},{\"id\":\"29\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"武清\"},{\"id\":\"30\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"宝坻\"},{\"id\":\"31\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"东丽\"},{\"id\":\"32\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"西青\"},{\"id\":\"33\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"北辰\"},{\"id\":\"34\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"宁河\"},{\"id\":\"35\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"汉沽\"},{\"id\":\"36\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"静海\"},{\"id\":\"37\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"津南\"},{\"id\":\"38\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"塘沽\"},{\"id\":\"39\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"大港\"},{\"id\":\"40\",\"province\":\"天津\",\"city\":\"天津\",\"district\":\"蓟县\"},{\"id\":\"41\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"重庆\"},{\"id\":\"42\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"永川\"},{\"id\":\"43\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"合川\"},{\"id\":\"44\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"南川\"},{\"id\":\"45\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"江津\"},{\"id\":\"46\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"万盛\"},{\"id\":\"47\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"渝北\"},{\"id\":\"48\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"北碚\"},{\"id\":\"49\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"巴南\"},{\"id\":\"50\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"长寿\"},{\"id\":\"51\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"黔江\"},{\"id\":\"52\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"万州\"},{\"id\":\"53\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"涪陵\"},{\"id\":\"54\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"开县\"},{\"id\":\"55\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"城口\"},{\"id\":\"56\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"云阳\"},{\"id\":\"57\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"巫溪\"},{\"id\":\"58\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"奉节\"},{\"id\":\"59\",\"province\":\"重庆\",\"city\":\"重庆\",\"district\":\"巫山\"}]";
    private static final String TAG = CityActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Gson gson=new Gson();
        try{
            ArrayList<City> cities=gson.fromJson(JSON,new TypeToken<List<City>>(){}.getType());
            Log.i(TAG,"cities:"+cities.size());
        }catch (Exception e){
            e.printStackTrace();
        }
        getCitys();
    }

    void getCitys(){
        Parameters params=new Parameters();
        params.add("city","长沙");
        JuheData.executeWithAPI(getApplicationContext()
                , 39
                , "http://web.juhe.cn:8080/environment/air/pm"
                , JuheData.GET
                , params
                , new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        Log.i(TAG,"HttpCode:"+i+",Result:\n"+s);
                        try {
                            JSONObject json=new JSONObject(s);
                            String result=json.getString("result");
                            Log.i(TAG,result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        Log.i(TAG,"HttpCode:"+i+",Result:\n"+s);
                    }
                });
    }
}
