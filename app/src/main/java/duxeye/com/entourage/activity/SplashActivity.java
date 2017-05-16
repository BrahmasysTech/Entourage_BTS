package duxeye.com.entourage.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.testfairy.TestFairy;
import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.constant.Constant;

public class SplashActivity extends AppCompatActivity {
    private static int SCREEN_DURATION = 2000;
    private LinearLayout btnLinearLayout;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        mActivity = this;


        btnLinearLayout = (LinearLayout) findViewById(R.id.btn_layout);
        btnLinearLayout.setVisibility(View.GONE);

        TestFairy.begin(this, "ad4be011f7705d15f1435382c854ace0b51d6c15");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SCREEN_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Utility.isConnectingToInternet()) {
                            btnLinearLayout.setVisibility(View.GONE);
                            if (Utility.getSharedPreferences(mActivity, Constant.MEMBERID).equalsIgnoreCase("")) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                                finish();
                            }
                        } else {
                            Utility.showInternetAlert(mActivity);
                            btnLinearLayout.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
        }).start();

    }

    public void generateException(View view){
        throw new NullPointerException("test crash");
    }
    public void refreshButton(View view) {
        if (Utility.isConnectingToInternet()) {
            btnLinearLayout.setVisibility(View.GONE);
            if (Utility.getSharedPreferences(mActivity, Constant.MEMBERID).equalsIgnoreCase("")) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                finish();

            } else {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                finish();
            }
        } else {
            Utility.showInternetAlert(mActivity);
            btnLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
