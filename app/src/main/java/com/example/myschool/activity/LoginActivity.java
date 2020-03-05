package com.example.myschool.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.example.myschool.R;
import com.example.myschool.pojo.Student;
import com.example.myschool.util.LoginExit;
import com.example.myschool.util.RandomSessionId;
import com.example.myschool.util.StudentLoginVerUtil;
import com.example.myschool.util.staticUrl;

import java.util.Map;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.forget)
    TextView forget;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.remember)
    RadioButton remember;
    // @Bind(R.id.loading)
    //  RelativeLayout loading;

    /**
     * 用户名
     */
    private String userNameText;
    /**
     * 密码
     */
    private String passwordText;
    /**
     * 是否自动登陆
     */
    private boolean autoLogin = true;
    /**
     * 是否记住密码
     */
    private boolean isRemember = true;

    /**
     * 是否切换账号过来
     */
    private boolean switchNum = false;

    /**
     * 加载中
     */
    private ProgressDialog loading = null;

    private RandomSessionId randomSessionId = new RandomSessionId(this).getInstances(this);

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (loading != null) {
                loading.dismiss();
                loading = null;
            }
            switch (msg.what) {
                case 0:
                    //loading.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    randomSessionId.makeSessionId();
                    break;
                case 1:
                    //记住密码
                    //SP保存，用于自动登陆
                    SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", userNameText);
                    editor.putString("pwd", passwordText);
                    //默认自动登陆
                    editor.putBoolean("auto_login", autoLogin);
                    //是否记住密码
                    editor.putBoolean("remember", isRemember);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "欢迎光临", Toast.LENGTH_SHORT).show();
                    Student student = (Student) msg.obj;
                    String json = JSONObject.toJSONString(student);
                    Intent intent = new Intent();
                    intent.putExtra("student", json);
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void initDate() {
        //SP保存，用于自动登陆
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", "");
        editor.putString("pwd", "");
        //默认自动登陆
        editor.putBoolean("auto_login", true);
        //是否记住密码
        editor.putBoolean("remember", true);
        editor.apply();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Map map = new StudentLoginVerUtil(LoginActivity.this).idVer(staticUrl.LOGIN, userNameText, passwordText);
                if (map.get("data") != null) {
                    message.what = 1;
                    message.obj = map.get("data");
                } else {
                    message.what = 0;
                    message.obj = map.get("msg");
                }
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        switchNum = intent.getBooleanExtra("switchNum", false);
        //默认记住密码
        remember.setVisibility(View.GONE);
        //SP保存，用于自动登陆
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        String name = sp.getString("name", "");
        String pwd = sp.getString("pwd", "");
        autoLogin = sp.getBoolean("auto_login", false);
        //当有记录时判断是否记住密码
        isRemember = sp.getBoolean("remember", false);
        //自动登陆
        if (isRemember && !"".equals(name) && !"".equals(pwd)) {
            username.setText(name);
            password.setText(pwd);
            userNameText = name;
            passwordText = pwd;
            //如果autoLoginString为true则自动登陆
            if (!switchNum && autoLogin) {
                if (loading == null) {
                    loading = ProgressDialog.show(this, "自动登陆", "加载中...");
                }
                //loading.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            initDate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

        //登陆点击监听
        login.setOnClickListener(this);
        // 是否记住密码
        remember.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        //获取session
        randomSessionId.getSessionId();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //正则判断一下输入是否合法
            case R.id.login:
                userNameText = username.getText().toString();
                passwordText = password.getText().toString();
                if (!"".equals(userNameText) && !"".equals(passwordText)) {
                    String pattern = "^[1-9]\\d*$";
                    //如果不是root用户则需判断账号是否为纯数字，是否合法
                    boolean isNumber = Pattern.matches(pattern, userNameText);
                    if (isNumber) {
                        if (loading == null) {
                            loading = ProgressDialog.show(this, "自动登陆", "加载中...");
                        }
                        //loading.setVisibility(View.VISIBLE);
                        initDate();
                    } else {
                        Toast.makeText(this, "账号格式错误（仅为数字）", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.remember:
                if (autoLogin) {
                    autoLogin = false;
                    remember.setChecked(false);
                } else {
                    autoLogin = true;
                    remember.setChecked(true);
                }
                break;
            default:
                break;
        }
    }
}
