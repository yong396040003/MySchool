package com.example.myschool.activity.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myschool.R;
import com.example.myschool.adapter.HomeAdapter;
import com.example.myschool.pojo.StudentUrl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    @Bind(R.id.grid_view)
    GridView gridView;
    private List<StudentUrl> studentUrls;

    @SuppressLint("ValidFragment")
    public HomeFragment(List<StudentUrl> studentUrls) {
        this.studentUrls = studentUrls;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        HomeAdapter homeAdapter = new HomeAdapter(studentUrls, getContext());
        gridView.setAdapter(homeAdapter);
    }

    private void initView() {
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
}
