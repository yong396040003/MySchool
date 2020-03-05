package com.example.myschool.activity.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.myschool.R;
import com.example.myschool.activity.MySelectTopicActivity;
import com.example.myschool.activity.MySettingActivity;
import com.example.myschool.activity.StudentWarningActivity;

import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */
@SuppressLint("ValidFragment")
public class MyFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.num)
    TextView num;
    @Bind(R.id.head)
    RelativeLayout head;
    @Bind(R.id.selected_topic)
    RelativeLayout selectedTopic;
    @Bind(R.id.di)
    TextView di;
    @Bind(R.id.student_warning)
    RelativeLayout studentWarning;
    @Bind(R.id.setting)
    RelativeLayout setting;


    private JSONObject jsonObject;

    @SuppressLint("ValidFragment")
    public MyFragment(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
    }

    private void initView() {
        name.setText(jsonObject.getString("name"));
        num.setText(jsonObject.getString("number"));

        setting.setOnClickListener(this);
        selectedTopic.setOnClickListener(this);
        studentWarning.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //进入设置页面
            case R.id.setting:
                intent.setClass(Objects.requireNonNull(getContext()), MySettingActivity.class);
                startActivity(intent);
                break;
            //进入毕设选题页面
            case R.id.selected_topic:
                intent.setClass(Objects.requireNonNull(getContext()), MySelectTopicActivity.class);
                startActivity(intent);
                break;
            //进入学籍预警页面
            case R.id.student_warning:
                intent.setClass(Objects.requireNonNull(getContext()), StudentWarningActivity.class);
                startActivity(intent);
                break;
        }
    }
}
