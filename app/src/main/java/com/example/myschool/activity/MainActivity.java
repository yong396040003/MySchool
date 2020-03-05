package com.example.myschool.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myschool.R;
import com.example.myschool.activity.fragment.main.CjFragment;
import com.example.myschool.activity.fragment.main.HomeFragment;
import com.example.myschool.activity.fragment.main.KbFragment;
import com.example.myschool.activity.fragment.main.MyFragment;
import com.example.myschool.pojo.StudentUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.home_icon)
    ImageView homeIcon;
    @Bind(R.id.home)
    TextView home;
    @Bind(R.id.home_bottom)
    LinearLayout homeBottom;
    @Bind(R.id.kb_icon)
    ImageView kbIcon;
    @Bind(R.id.kb)
    TextView kb;
    @Bind(R.id.kb_bottom)
    LinearLayout kbBottom;
    @Bind(R.id.cj_icon)
    ImageView cjIcon;
    @Bind(R.id.cj)
    TextView cj;
    @Bind(R.id.my_icon)
    ImageView myIcon;
    @Bind(R.id.my)
    TextView my;
    @Bind(R.id.cj_bottom)
    LinearLayout cjBottom;
    @Bind(R.id.my_bottom)
    LinearLayout myBottom;
    private boolean isBack = false;

    private HomeFragment homeFragment;
    private KbFragment kbFragment;
    private CjFragment cjFragment;
    private MyFragment myFragment;

    private List<StudentUrl> studentUrls;

    private JSONObject student;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initDate() {
        Intent intent = getIntent();
        String json = intent.getStringExtra("student");
        JSONObject jsonObject = JSON.parseObject(json);
        student = new JSONObject();
        student.put("name",jsonObject.getString("name"));
        student.put("number",jsonObject.getString("number"));
        JSONArray jsonArray = jsonObject.getJSONArray("studentUrlList");
        studentUrls = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String name = jsonArray.getJSONObject(i).getString("name");
            switch (name) {
                case "首页":
                    break;
                case "我的桌面":
                    break;
                case "学籍成绩":
                    break;
                case "课程成绩查询":
                    break;
                case "班级课表查询":
                    break;
                case "学期理论课表":
                    break;
                default:
                    StudentUrl studentUrl = JSONObject.parseObject(jsonArray.getJSONObject(i).toJSONString(), StudentUrl.class);
                    studentUrls.add(studentUrl);
                    break;
            }
        }
    }

    @Override
    public void initView() {
        homeBottom.setOnClickListener(this);
        kbBottom.setOnClickListener(this);
        cjBottom.setOnClickListener(this);
        myBottom.setOnClickListener(this);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        homeFragment = new HomeFragment(studentUrls);
        fragmentTransaction.add(R.id.frameLayout, homeFragment).commit();
    }

    /**
     * 重置底部按钮
     */
    @SuppressLint("ResourceAsColor")
    private void isSelect() {
        homeIcon.setBackground(getResources().getDrawable(R.mipmap.home1, null));
        kbIcon.setBackground(getResources().getDrawable(R.mipmap.kb1, null));
        cjIcon.setBackground(getResources().getDrawable(R.mipmap.cj1, null));
        myIcon.setBackground(getResources().getDrawable(R.mipmap.my1, null));

        home.setTextColor(getResources().getColor(R.color.font));
        kb.setTextColor(getResources().getColor(R.color.font));
        cj.setTextColor(getResources().getColor(R.color.font));
        my.setTextColor(getResources().getColor(R.color.font));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initDate();
        initView();
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        isBack = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        switch (v.getId()) {
            case R.id.home_bottom:
                isSelect();
                homeIcon.setBackground(getResources().getDrawable(R.mipmap.home, null));
                home.setTextColor(getResources().getColor(R.color.icon_font_true));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment(studentUrls);
                    fragmentTransaction.add(R.id.frameLayout, homeFragment).commit();
                } else {
                    fragmentTransaction.show(homeFragment).commit();
                }
                break;
            case R.id.kb_bottom:
                isSelect();
                kbIcon.setBackground(getResources().getDrawable(R.mipmap.kb, null));
                kb.setTextColor(getResources().getColor(R.color.icon_font_true));
                if (kbFragment == null) {
                    kbFragment = new KbFragment();
                    fragmentTransaction.add(R.id.frameLayout, kbFragment).commit();
                } else {
                    fragmentTransaction.show(kbFragment).commit();
                }
                break;
            case R.id.cj_bottom:
                isSelect();
                cjIcon.setBackground(getResources().getDrawable(R.mipmap.cj, null));
                cj.setTextColor(getResources().getColor(R.color.icon_font_true));
                if (cjFragment == null) {
                    cjFragment = new CjFragment();
                    fragmentTransaction.add(R.id.frameLayout, cjFragment).commit();
                } else {
                    fragmentTransaction.show(cjFragment).commit();
                }
                break;
            case R.id.my_bottom:
                isSelect();
                myIcon.setBackground(getResources().getDrawable(R.mipmap.my, null));
                my.setTextColor(getResources().getColor(R.color.icon_font_true));
                if (myFragment == null) {
                    myFragment = new MyFragment(student);
                    fragmentTransaction.add(R.id.frameLayout, myFragment).commit();
                } else {
                    fragmentTransaction.show(myFragment).commit();
                }
                break;
            default:
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (kbFragment != null) {
            fragmentTransaction.hide(kbFragment);
        }
        if (cjFragment != null) {
            fragmentTransaction.hide(cjFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
    }
}
