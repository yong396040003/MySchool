package com.example.myschool.activity.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.R;
import com.example.myschool.util.MyScheduleUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 课表
 */
public class KbFragment extends Fragment {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.Mon)
    LinearLayout Mon;
    @Bind(R.id.Tus)
    LinearLayout Tus;
    @Bind(R.id.Wed)
    LinearLayout Wed;
    @Bind(R.id.Thu)
    LinearLayout Thu;
    @Bind(R.id.Fri)
    LinearLayout Fri;
    @Bind(R.id.Sat)
    LinearLayout Sat;
    @Bind(R.id.Sun)
    LinearLayout Sun;
    @Bind(R.id.bg)
    LinearLayout bg;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.error)
    TextView error;
    @Bind(R.id.common)
    Button common;
    @Bind(R.id.loading)
    RelativeLayout loading;

    /**
     * 加载框
     */
    private ProgressDialog dialog = null;

    /**
     * 周数
     */
    private int zc = 1;
    /**
     * 当前周数
     */
    private int nowZc = 1;

    /**
     * 判断是否是第一次进入
     */
    private boolean isOne = false;

    /**
     * 今天周几
     * 为了区分0所有设为Integer
     */
    private Integer dayWeek;

    /**
     * 根据时间计算周数
     *
     * @param date
     * @return
     */
    private void judge(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(date);
        // 指示一个星期中的某天。
        dayWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayWeek < 0) {
            dayWeek = 7;
        }

        //月份
        int month = cal.get(Calendar.MONTH) + 1;
        //判断如今是上、下学期
        //下学期
        if (month >= 9) {
            int year = cal.get(Calendar.YEAR);
            try {
                Date da = f.parse(year + "-9-1");

                //计算当前是第几周
                nowZc = (int) ((date.getTime() - da.getTime()) / (1000 * 3600 * 24) / 7 + 1);

                zc = nowZc;

                isOne = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //上学期
        } else {

        }

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
            if (msg.obj == null) {
                //显示加载布局
                loading.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    List<String> strings = (List<String>) msg.obj;
                    //课表布局初始化
                    initKBView();
                    //加载课表数据到布局上
                    initViewData(strings);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        private void initKBView() {
            Mon.removeAllViews();
            Tus.removeAllViews();
            Wed.removeAllViews();
            Thu.removeAllViews();
            Fri.removeAllViews();
            Sat.removeAllViews();
            Sun.removeAllViews();
        }

        private void initViewData(List<String> strings) {
            //从第一周到星期天
            for (int i = 0; i < 7; i++) {
                line = 1;
                //数据是以行的形式存在
                for (int j = i; j < strings.size(); j += 7) {
                    switch (j % 7) {
                        case 0:
                            addView(j, strings, Mon);
                            break;
                        case 1:
                            addView(j, strings, Tus);
                            break;
                        case 2:
                            addView(j, strings, Wed);
                            break;
                        case 3:
                            addView(j, strings, Thu);
                            break;
                        case 4:
                            addView(j, strings, Fri);
                            break;
                        case 5:
                            addView(j, strings, Sat);
                            break;
                        case 6:
                            addView(j, strings, Sun);
                            break;
                    }

                }
            }
        }

        //合并几行
        private int line = 1;

        /**
         * 自定义每天上课课表
         * 课程相同则合并在一起
         * @param i
         * @param strings
         * @param view
         */
        private void addView(int i, List<String> strings, LinearLayout view) {
            //当天是周几就在周几后面画一个背景
            // 默认选中的样式
            // 且周数是本周才行
            if (dayWeek - 1 == i && zc == nowZc) {
                view.setBackgroundColor(R.drawable.kb_now_day);
            }


            //最后一个
            if (i + 7 >= strings.size()) {
                TextView textView = createTextView(strings.get(i), line, view);
                view.addView(textView);
                line = 1;
            } else {
                //行数不同 列数相同时
                if (strings.get(i).equals(strings.get(i + 7))) {
                    line++;
                    //行数不同 列数不同时
                } else {
                    TextView textView = createTextView(strings.get(i), line, view);
                    view.addView(textView);
                    line = 1;
                }
            }
        }

        /**
         * 判断是否合并 num合并几个
         * @param text
         * @param num
         * @return
         */
        @SuppressLint("Range")
        TextView createTextView(String text, int num, LinearLayout linearLayout) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    linearLayout.getWidth() - 10,
                    (linearLayout.getHeight() / 5 * num) - 20
            );
            params.setMargins(5, 10, 5, 10);
            textView.setLayoutParams(params);
            if (!"".equals(text)) {
                textView.setBackgroundResource(R.drawable.kb_bg);
                textView.setAlpha(40);
            }
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(text);
            return textView;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kb, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        if (!isOne) {
            //判断今天是周几 0星期天 1星期一 .....
            judge(new Date());
        }


        //根据周数返回课表数据
        loadData(zc);
    }

    /**
     * 加载课表数据
     *
     * @param i
     * @return
     */
    private void loadData(final int i) {
        if (dialog == null) {
            dialog = ProgressDialog.show(
                    getContext(),
                    "加载中...",
                    "请稍等片刻...",
                    true
            );
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = new MyScheduleUtil(i).getMySchedule();
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("Range")
    private void initView() {
        bg.setAlpha(10);

        spinner.setDropDownWidth(200); //下拉宽度
        spinner.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner.setDropDownVerticalOffset(100); //下拉的纵向偏移
        //spinner.setBackgroundColor(AppUtil.getColor(instance,R.color.wx_bg_gray)); //下拉的背景色
        //spinner mode ： dropdown or dialog , just edit in layout xml
        //spinner.setPrompt("Spinner Title"); //弹出框标题，在dialog下有效


        final String[] spinnerItems = new String[20];
        for (int i = 1; i <= 20; i++) {
            spinnerItems[i - 1] = i + "";
        }
        //自定义选择填充后的字体样式
        //只能是textview样式，否则报错：ArrayAdapter requires the resource ID to be a TextView
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                R.layout.item_select, spinnerItems);
        //自定义下拉的字体样式
        spinnerAdapter.setDropDownViewResource(R.layout.item_drop);
        //这个在不同的Theme下，显示的效果是不同的
        //spinnerAdapter.setDropDownViewTheme(Theme.LIGHT);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zc = position + 1;
                loadData(zc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //重新加载数据
        common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏加载布局
                loading.setVisibility(View.GONE);
                loadData(zc);
            }
        });
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
