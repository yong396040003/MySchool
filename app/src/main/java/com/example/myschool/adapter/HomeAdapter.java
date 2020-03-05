package com.example.myschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myschool.R;
import com.example.myschool.pojo.StudentUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页adapter
 *
 * @author yong
 * @time 2019/12/12 13:19
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    private List<StudentUrl> studentUrls;

    public HomeAdapter(List<StudentUrl> studentUrls, Context context) {
        this.studentUrls = studentUrls;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (studentUrls == null) {
            return 0;
        } else {
            return studentUrls.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_adapter_grid_list, null);
        }

        ImageView imageView = convertView.findViewById(R.id.home_grid_icon);
        TextView textView = convertView.findViewById(R.id.home_grid_text);

        textView.setText(studentUrls.get(position).getName());
        isSetText(imageView, studentUrls.get(position).getName());

        return convertView;
    }

    private void isSetText(ImageView imageView, String name) {
        switch (name) {
            case "培养管理":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.py, null));
                break;
            case "考试报名":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.ks, null));
                break;
            case "实践环节":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.sj, null));
                break;
            case "教学评价":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.jxpj, null));
                break;
            case "选课中心":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.xkzx, null));
                break;
            case "学生评教":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.pj, null));
                break;
            case "培养方案":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.fa, null));
                break;
            case "已收留言":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.ly, null));
                break;
            case "已收公告":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.gg, null));
                break;
            case "重修报名选课":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.cx, null));
                break;
            case "学籍卡片":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.xj, null));
                break;
            case "社会考试报名":
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.shks, null));
                break;
            default:
                break;
        }
    }
}
