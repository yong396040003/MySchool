package com.example.myschool.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.R;
import com.example.myschool.util.LoginExit;
import com.example.myschool.util.MyDialog;
import com.example.myschool.util.RandomSessionId;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的
 * 设置
 *
 * @author yong
 * @time 2019/12/16 19:28
 */
public class MySettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.switch_auto_login)
    Switch switchAutoLogin;
    @Bind(R.id.auto_login)
    RelativeLayout autoLogin;
    @Bind(R.id.di)
    TextView di;
    @Bind(R.id.switch_remember_pwd)
    Switch switchRememberPwd;
    @Bind(R.id.remember_pwd)
    RelativeLayout rememberPwd;
    @Bind(R.id.logout)
    RelativeLayout logout;
    @Bind(R.id.switch_number)
    RelativeLayout switchNumber;
    @Bind(R.id.back)
    ImageView back;

    private String name;
    private String pwd;

    /**
     * 是否自动登陆
     */
    private boolean autoLogin1;
    /**
     * 是否记住密码
     */
    private boolean isRemember;

    private RandomSessionId randomSessionId = new RandomSessionId(this).getInstances(this);

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                Intent intent = new Intent();
                intent.putExtra("switchNum", true);
                intent.setClass(MySettingActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MySettingActivity.this, "网络连接超时", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void initDate() {
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        autoLogin1 = sp.getBoolean("auto_login", false);
        isRemember = sp.getBoolean("remember", false);
        name = sp.getString("name", "");
        pwd = sp.getString("pwd", "");
    }

    @Override
    public void initView() {
        switchAutoLogin.setChecked(autoLogin1);
        switchRememberPwd.setChecked(isRemember);

        switchAutoLogin.setOnClickListener(this);
        switchRememberPwd.setOnClickListener(this);

        logout.setOnClickListener(this);
        switchNumber.setOnClickListener(this);

        back.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initDate();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.switch_auto_login:
                setSp(switchAutoLogin.isChecked(), switchRememberPwd.isChecked());
                break;
            case R.id.switch_remember_pwd:
                setSp(switchAutoLogin.isChecked(), switchRememberPwd.isChecked());
                break;
            case R.id.switch_number:
                final MyDialog dialog = new MyDialog(MySettingActivity.this);
                dialog.setMessage("切换账号需要登出你当前操作，请确认你的操作")
                        .setTitle("系统提示")
                        .setSingle(false).setOnClickBottomListener(new MyDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.obj = new LoginExit().loginExit();
                                randomSessionId.makeSessionId();
                                mHandler.sendMessage(message);
                                dialog.dismiss();
                            }
                        }).start();
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.logout:
                final MyDialog dialog1 = new MyDialog(MySettingActivity.this);
                dialog1.setMessage("是否退出当前应用")
                        .setTitle("系统提示")
                        .setSingle(false).setOnClickBottomListener(new MyDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setSp(false, switchRememberPwd.isChecked());
                                finishAffinity();
                                randomSessionId.makeSessionId();
                                dialog1.dismiss();
                            }
                        }).start();
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog1.dismiss();
                    }
                }).show();
                break;
        }
    }

    private void setSp(boolean autoLogin, boolean remember) {
        if (!remember) {
            //SP保存，用于自动登陆
            SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", "");
            editor.putString("pwd", "");
            //默认自动登陆
            editor.putBoolean("auto_login", autoLogin);
            //是否记住密码
            editor.putBoolean("remember", false);
            editor.apply();
        } else {
            //SP保存，用于自动登陆
            SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", name);
            editor.putString("pwd", pwd);
            //默认自动登陆
            editor.putBoolean("auto_login", autoLogin);
            //是否记住密码
            editor.putBoolean("remember", true);
            editor.apply();
        }
    }
}
