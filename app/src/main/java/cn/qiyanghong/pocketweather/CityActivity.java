package cn.qiyanghong.pocketweather;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.qiyanghong.pocketweather.entity.City;

public class CityActivity extends AppCompatActivity {
    private static final String JSON = "{\"resultcode\":\"200\",\"reason\":\"successed\",\"result\":[{\"id\":\"1\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"北京\"},{\"id\":\"2\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"海淀\"},{\"id\":\"3\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"朝阳\"},{\"id\":\"4\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"顺义\"},{\"id\":\"5\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"怀柔\"},{\"id\":\"6\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"通州\"},{\"id\":\"7\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"昌平\"},{\"id\":\"8\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"延庆\"},{\"id\":\"9\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"丰台\"},{\"id\":\"10\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"石景山\"},{\"id\":\"11\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"大兴\"},{\"id\":\"12\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"房山\"},{\"id\":\"13\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"密云\"},{\"id\":\"14\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"门头沟\"},{\"id\":\"15\",\"province\":\"北京\",\"city\":\"北京\",\"district\":\"平谷\"},{\"id\":\"16\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"上海\"},{\"id\":\"17\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"闵行\"},{\"id\":\"18\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"宝山\"},{\"id\":\"19\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"嘉定\"},{\"id\":\"20\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"南汇\"},{\"id\":\"21\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"金山\"},{\"id\":\"22\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"青浦\"},{\"id\":\"23\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"松江\"},{\"id\":\"24\",\"province\":\"上海\",\"city\":\"上海\",\"district\":\"奉贤\"}],\"error_code\":0}";
    private static final String TAG = CityActivity.class.getName();

    private ExpandableListView listView;
    private ImageButton btn_search;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_city);

        listView = (ExpandableListView) findViewById(R.id.lv_city);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputCitynameDialog();
            }
        });
        showCityList();
    }

    private void showInputCitynameDialog() {
        View dialogcontent = getLayoutInflater().inflate(R.layout.simplle_edittext, null, false);
        final EditText input = (EditText) dialogcontent.findViewById(R.id.edittext1);
        input.setHint("请输入城市名称");

        new AlertDialog.Builder(context).setView(dialogcontent).setTitle("搜索城市")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cityname = input.getText().toString();
                        L.i(TAG, "cityname:" + cityname);
                        List result = searchCity(cityname);
                        showChooseCityDialog(result);
                    }
                }).show();
    }

    private void showChooseCityDialog(final List<City> result) {
        if (result == null || result.size() == 0) {
            Toast.makeText(context, "没有查询到结果!", Toast.LENGTH_SHORT).show();
            return;
        }
        final int size= result.size();
        String[] cityname =new String[size];

        //获取名字列表
        for (int i=0;i<size;i++) {
            cityname[i]=result.get(i).toString();
        }

        new AlertDialog.Builder(context).setTitle("选择城市")
                .setSingleChoiceItems(cityname, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        L.i(TAG, "Selected:" + which);
                        setActivityResult(result.get(which).getId());
                        dialog.dismiss();
                    }
                }).show();
    }

    private void setActivityResult(int cityId) {
        //// TODO: 2016/5/9 设置返回值，并且将某些后台耗时操作移动到Servi中
        L.i(TAG,"->setActivityResult:"+cityId);
    }

    private List<City> searchCity(String cityname) {
        List<City> result = null;
        try {
            final Dao<City, Integer> cityDao = DBHelper.getHelper(context).getCityDao();
            result = cityDao.queryForEq("district", cityname);
            if (result.size() == 0) {
                result = cityDao.queryForEq("city", cityname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void showCityList() {
        String[] provinces = new String[40];      //列表大小跟cities是一样的
        final List<List<City>> cities = new ArrayList<>();
        try {
            Dao<City, Integer> cityDao = DBHelper.getHelper(getApplicationContext()).getCityDao();
            List<City> provinceGroup = cityDao.queryBuilder().selectColumns("province").distinct().query();
            //获得省级目录
            for (int i = 0; i < provinceGroup.size(); i++) {       //根据省名填充cities
                String province = provinceGroup.get(i).getProvince();
                provinces[i] = province;
                L.i(TAG, "province:" + province);
                List<City> city = cityDao.queryForEq("province", province);
                cities.add(city);
                L.i(TAG, "descript:" + city.get(0).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //往List里填充数据
        final MyExpandableLVAdapter adapter = new MyExpandableLVAdapter(getApplicationContext(), provinces, cities);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                City city = (City) adapter.getChild(groupPosition, childPosition);
                L.i(TAG, "id:" + city.getId() + ",desc:" + city.toString());
                return false;
            }
        });
    }

    private void parseCitys(String response) {
        try {
            final Dao<City, Integer> cityDao = DBHelper.getHelper(getApplicationContext()).getCityDao();

            JSONObject jsonObject = new JSONObject(response);
            String errercode = jsonObject.getString("error_code");
            if (!errercode.equals("0")) throw new RequestErrorException();

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

            L.i(TAG, String.valueOf(cityDao.countOf()));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RequestErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void refreshCitys() {
        JuheData.executeWithAPI(getApplicationContext()
                , 39
                , "http://v.juhe.cn/weather/citys"
                , JuheData.GET
                , null
                , new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        Log.i(TAG, "HttpCode:" + i + ",Result:\n" + s);
                        parseCitys(s);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        Log.i(TAG, "HttpCode:" + i + ",Result:\n" + s);
                    }
                });
    }

}
