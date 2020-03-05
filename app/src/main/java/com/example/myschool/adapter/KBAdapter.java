package com.example.myschool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myschool.R;

import java.util.List;

/**
 * 课表
 *
 * @author yong
 * @time 2019/12/13 15:27
 */
public class KBAdapter extends BaseAdapter {
    private List<String> strings;
    private Context context;

    public KBAdapter(List<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (strings == null) {
            return 0;
        } else {
            return strings.size();
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kb_adapter_grid_list, null);
        }
        convertView.setLayoutParams(new RelativeLayout.LayoutParams(
                parent.getWidth() / 7 - 1,
                parent.getHeight() / (strings.size() / 7) - 5
        ));
        TextView textView = convertView.findViewById(R.id.text);

        textView.setText(strings.get(position));
        if (!"".equals(strings.get(position))){
            convertView.setBackgroundColor(R.color.colorAccent);
        }

        return convertView;
    }
}
