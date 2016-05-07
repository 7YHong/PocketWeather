package cn.qiyanghong.pocketweather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.thinkland.sdk.android.JuheSDKInitializer;

public class LaunchActivity extends Activity {

    private static final String TAG = LaunchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.i(TAG, "SDK_INT:" + Build.VERSION.SDK_INT);
        Log.i(TAG, "VERSION_CODES:" + Build.VERSION_CODES.M);
        checkAndLaunch();

//        JuheSDKInitializer.initialize(getApplicationContext());
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        finish();
    }

    private void checkAndLaunch() {
        //若没有权限则请求，然后返回
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    ||checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION}
                        , 0);
                return;
            }
        }
        //权限没有问题
        JuheSDKInitializer.initialize(getApplicationContext());
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED
                    ||grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_LONG).show();
                finish();
            }else
                checkAndLaunch();
        }
    }
}
