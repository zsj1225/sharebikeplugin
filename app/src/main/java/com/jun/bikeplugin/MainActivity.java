package com.jun.bikeplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //自动联网查询
    private static final String AUTOCONNECTNETSEARCH = "auto_connect_net_search";
    private static final String AUTOBACKUPPWD = "auto_backup_pwd";
    private static final String LASTUPDATETIME = "last_update_time";


    private  Button btnSwitch,btnSync,btnSelect,btnEnsure,btnExport,btnInport;
    private TextView tvTips,tvSyncTips;
    private CheckBox cbNetSearch,cbNetbackup;
    private EditText etWebsite;


    private boolean mAutoGetPwsFromNet;
    private boolean mAutoBackupPwsToNet;
    private String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnSync = (Button) findViewById(R.id.btnSync);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnEnsure = (Button) findViewById(R.id.btnEnsure);
        btnExport = (Button) findViewById(R.id.btnExport);
        btnInport = (Button) findViewById(R.id.btnInport);

        tvTips = (TextView) findViewById(R.id.tvTips);
        tvSyncTips = (TextView) findViewById(R.id.tvSyncTips);

        cbNetSearch= (CheckBox) findViewById(R.id.cbNetsearch);
        cbNetbackup= (CheckBox) findViewById(R.id.cbNetbackup);

        etWebsite = (EditText) findViewById(R.id.etWebsite);


        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initListener() {
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });

        cbNetSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAutoGetPwsFromNet = isChecked;
                Utils.SaveData(MainActivity.this,AUTOCONNECTNETSEARCH,isChecked);
            }
        });

        cbNetbackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAutoBackupPwsToNet = isChecked;
                Utils.SaveData(MainActivity.this,AUTOBACKUPPWD,isChecked);
            }
        });
    }

    private void initData() {
        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            tvTips.setText("未开启自动获取共享单车密码功能,请点击按钮,在打开的无障碍设置页面中,打开小军外挂功能.");
            btnSwitch.setVisibility(View.VISIBLE);
        }else {
            tvTips.setText("已开启自动获取共享单车密码功能,尽情使用吧");
            btnSwitch.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mAutoGetPwsFromNet = Utils.getBooleabData(this, AUTOCONNECTNETSEARCH);
        mAutoBackupPwsToNet = Utils.getBooleabData(this, AUTOBACKUPPWD);
        mLastUpdateTime = Utils.getStringData(this, LASTUPDATETIME);

        cbNetSearch.setChecked(mAutoGetPwsFromNet);
        cbNetbackup.setChecked(mAutoBackupPwsToNet);

        if(mLastUpdateTime == null){
            tvSyncTips.setText("没有检查到更新记录,请点击上方按钮从服务器更新数据吧");
        }else {
            tvSyncTips.setText("上次更新于:" + mLastUpdateTime);
        }

    }


    // To check if service is enabled
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + BikeAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

}
