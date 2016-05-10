package cn.qiyanghong.pocketweather;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import cn.qiyanghong.pocketweather.entity.City;

public class CityActivity extends AppCompatActivity {
    private static final String TAG = CityActivity.class.getName();

    private Context context;

    private ExpandableListView listView;
    private ImageButton btn_search
            ,btn_refersh;

    private boolean isNeedRefresh=true;

    private WeatherService weatherService;

    /**
     * 在以下几个地方显示：
     * onCreate
     * refresh_btn->click
     * searchDialog->click
     *
     * 在以下几个地方消失:
     * onCityUpdated
     * onSearchDone
     */
    private AlertDialog waitingDialog;

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            weatherService=((WeatherService.WeatherServiceBinder)service).getService();
            weatherService.registerCallback(mCityCallback);
            if (isNeedRefresh) {
                weatherService.refreshCity();
            }else {
                try {
                    weatherService.getCities();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            L.i(TAG,"->onServiceConnected\tisNeedRefresh:"+isNeedRefresh);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            weatherService.unregisterCallback(mCityCallback);
        }
    };

    private OnCityCallback mCityCallback=new OnCityCallback() {
        @Override
        public void onCityUpdate(String[] province, List<List<City>> cities) {
            showCityList(province, cities);
            dismissWaitingDialog();
        }

        @Override
        public void onSearchDone(List<City> searchResult) {
            dismissWaitingDialog();
            showChooseCityDialog(searchResult);
            L.i(TAG,"->onSearchDone");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_city);

        isNeedRefresh=!SPUtils.contains(getApplicationContext(),"cityname");

//        progressbar= (ProgressBar) findViewById(R.id.progressbar);
        listView = (ExpandableListView) findViewById(R.id.lv_city);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waitingDialog.isShowing())return;
                showSearchCityDialog();
            }
        });

        btn_refersh= (ImageButton) findViewById(R.id.btn_refresh);
        btn_refersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waitingDialog.isShowing())return;
                showWaitingDialog();
                weatherService.refreshCity();
            }
        });
        waitingDialog=new AlertDialog.Builder(CityActivity.this)
                .setView(R.layout.progressbar_dialog)
                .create();
        waitingDialog.setCanceledOnTouchOutside(false);
        showWaitingDialog();

        Intent serviceIntent = new Intent(getApplicationContext(), WeatherService.class);
        bindService(serviceIntent, conn, BIND_AUTO_CREATE);
    }

    private void showWaitingDialog() {
        if (!waitingDialog.isShowing())
            waitingDialog.show();
    }

    private void dismissWaitingDialog() {
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    private void showSearchCityDialog() {
        View dialogcontent = getLayoutInflater().inflate(R.layout.simplle_edittext, null, false);
        final EditText input = (EditText) dialogcontent.findViewById(R.id.edittext1);
        input.setHint("请输入城市名称");

        new AlertDialog.Builder(context).setView(dialogcontent).setTitle("搜索城市")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cityname = input.getText().toString();
                        L.i(TAG, "cityname:" + cityname);
                        showWaitingDialog();
                        weatherService.searchCity(cityname);
                        L.i(TAG,"->showSearchCityDialog\t搜索城市");
                    }
                }).show();
    }

    private void showChooseCityDialog(final List<City> result) {
        if (result == null || result.size() == 0) {
            Toast.makeText(context, "没有查询到结果!", Toast.LENGTH_SHORT).show();
            return;
        }
        final int size = result.size();
        String[] cityname = new String[size];

        //获取名字列表
        for (int i = 0; i < size; i++) {
            cityname[i] = result.get(i).toString();
        }

        new AlertDialog.Builder(context).setTitle("选择城市")
                .setSingleChoiceItems(cityname, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        L.i(TAG, "Selected:" + which);
                        dialog.dismiss();
                        setResultAndFinish(result.get(which).getId(),result.get(which).getCity());
                    }
                }).show();
    }

    private void setResultAndFinish(int cityid, String cityname) {
        L.i(TAG, "->setResultAndFinish\tcityid:" +cityid );
        L.i(TAG, "->setResultAndFinish\tcityname:" + cityname);
        SPUtils.put(getApplicationContext(),"cityname",cityname);
        SPUtils.put(getApplicationContext(),"cityid",cityid);
        setResult(RESULT_OK);
        finish();
    }

//    private List<City> searchCity(String cityname) {
//        List<City> result = null;
//        try {
//            final Dao<City, Integer> cityDao = DBHelper.getHelper(context).getCityDao();
//            result = cityDao.queryForEq("district", cityname);
//            if (result.size() == 0) {
//                result = cityDao.queryForEq("city", cityname);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    private void showCityList(String[] provinces,final List<List<City>> cities) {
        //往List里填充数据
        final MyExpandableLVAdapter adapter = new MyExpandableLVAdapter(getApplicationContext(), provinces, cities);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                City city = (City) adapter.getChild(groupPosition, childPosition);
                L.i(TAG, "id:" + city.getId() + ",desc:" + city.toString());
                setResultAndFinish(city.getId(),city.getCity());
                return false;
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        if (waitingDialog.isShowing()){
//            dismissWaitingDialog();
//            return;
//        }
//        super.onBackPressed();
//    }

    interface OnCityCallback {
        void onCityUpdate(String[] province, List<List<City>> cities);

        void onSearchDone(List<City> searchResult);
    }
}
