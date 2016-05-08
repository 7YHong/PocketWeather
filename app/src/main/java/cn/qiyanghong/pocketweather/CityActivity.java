package cn.qiyanghong.pocketweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qiyanghong.pocketweather.entity.City;

public class CityActivity extends AppCompatActivity {
    private static final String JSON = "{\"resultcode\":\"200\",\"reason\":\"successed\",\"result\":[{\"id\":\"1\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"北京\"},{\"id\":\"2\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"海淀\"},{\"id\":\"3\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"朝阳\"},{\"id\":\"4\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"顺义\"},{\"id\":\"5\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"怀柔\"},{\"id\":\"6\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"通州\"},{\"id\":\"7\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"昌平\"},{\"id\":\"8\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"延庆\"},{\"id\":\"9\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"丰台\"},{\"id\":\"10\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"石景山\"},{\"id\":\"11\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"大兴\"},{\"id\":\"12\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"房山\"},{\"id\":\"13\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"密云\"},{\"id\":\"14\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"门头沟\"},{\"id\":\"15\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"平谷\"},{\"id\":\"16\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"上海\"},{\"id\":\"17\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"闵行\"},{\"id\":\"18\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"宝山\"},{\"id\":\"19\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"嘉定\"},{\"id\":\"20\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"南汇\"},{\"id\":\"21\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"金山\"},{\"id\":\"22\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"青浦\"},{\"id\":\"23\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"松江\"},{\"id\":\"24\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"奉贤\"}],\"error_code\":0}";
    private static final String TAG = CityActivity.class.getName();

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        listView= (ExpandableListView) findViewById(R.id.lv_city);

        getCitys();
    }

    private void getCitys() {
        try {
            Dao<City,Integer> cityDao=DBHelper.getHelper(getApplicationContext()).getCityDao();

            JSONObject jsonObject=new JSONObject(JSON);
            String errercode = jsonObject.getString("error_code");
            if (!errercode.equals("0")) throw new RequestErrorException();

            //然后解析数据
            JSONArray result=jsonObject.getJSONArray("result");
            City[] cities=new Gson().fromJson(result.toString(),City[].class);
            for (City c:cities){
                cityDao.createIfNotExists(c);
            }

            L.i(TAG,"cities:"+String.valueOf(cities.length));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RequestErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    void getCitys(){
//        JuheData.executeWithAPI(getApplicationContext()
//                , 39
//                , "http://v.juhe.cn/weather/citys"
//                , JuheData.GET
//                , null
//                , new DataCallBack() {
//                    @Override
//                    public void onSuccess(int i, String s) {
//                        Log.i(TAG,"HttpCode:"+i+",Result:\n"+s);
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s, Throwable throwable) {
//                        Log.i(TAG,"HttpCode:"+i+",Result:\n"+s);
//                    }
//                });
//    }
}
