package com.example.myschool.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.example.myschool.R;
import com.example.myschool.pojo.XJYJ;
import com.example.myschool.util.StudentWarning;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 学籍预警
 *
 * @author yong
 * @time 2019/12/19 23:44
 */
public class StudentWarningActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.table)
    SmartTable table;
    @Bind(R.id.page)
    EditText page;
    @Bind(R.id.page_total)
    TextView pageTotal;
    @Bind(R.id.pre)
    Button pre;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.table_b)
    RelativeLayout tableB;
    @Bind(R.id.error)
    TextView error;
    @Bind(R.id.common)
    Button common;
    @Bind(R.id.loading)
    RelativeLayout loading;

    private StudentWarning studentWarning;

    /**
     * 加载框
     */
    private ProgressDialog dialog = null;

    //当前页数
    private int nowPage = 1;
    //尾页
    private int endPage = 10000;

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
                Toast.makeText(StudentWarningActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
            } else {
                final List<XJYJ> xjyjList = (List<XJYJ>) msg.obj;
                table.setData(xjyjList);
                table.getConfig().setShowTableTitle(false);
                table.getConfig().setShowXSequence(false);
                table.getConfig().setShowYSequence(false);
                table.setZoom(true);

                tableB.setVisibility(View.VISIBLE);
                //设置总页数和总行数
                pageTotal.setText(studentWarning.pageTotal());
                String end = studentWarning.pageTotal().split(" ")[0].replace("共", "").replace("页", "");
                endPage = Integer.parseInt(end);
                page.setText(String.valueOf(nowPage));
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_student_warning;
    }

    @Override
    public void initDate() {
        loadData(nowPage);
    }

    @Override
    public void initView() {
        //返回
        back.setOnClickListener(this);
        //上一页
        pre.setOnClickListener(this);
        //下一页
        next.setOnClickListener(this);
        //重新加载
        common.setOnClickListener(this);
        //根据页数跳转
        page.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String p = v.getText().toString().trim();
                    hideInput();
                    int p1 = Integer.parseInt(p);
                    if (p1 >= endPage) {
                        Toast.makeText(StudentWarningActivity.this, "超出最大页数", Toast.LENGTH_SHORT).show();
                        page.setText(String.valueOf(nowPage));
                    } else {
                        nowPage = p1;
                        loadData(nowPage);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 加载数据
     *
     * @param page
     */
    public void loadData(final int page) {
        if (dialog == null) {
            dialog = ProgressDialog.show(
                    StudentWarningActivity.this,
                    "学籍预警", "加载中...");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                studentWarning = new StudentWarning();
                message.obj = studentWarning.studentWarning(page);
                mHandler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView();
        initDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //重新加载
            case R.id.common:
                loading.setVisibility(View.GONE);
                loadData(nowPage);
                break;
            //上一页
            case R.id.pre:
                if (nowPage <= 1) {
                    Toast.makeText(StudentWarningActivity.this, "当前已是第一页", Toast.LENGTH_SHORT).show();
                } else {
                    nowPage = nowPage - 1;
                    loadData(nowPage);
                }
                break;
            //下一页
            case R.id.next:
                if (nowPage == endPage) {
                    Toast.makeText(StudentWarningActivity.this, "当前已是最后一页", Toast.LENGTH_SHORT).show();
                } else {
                    nowPage = nowPage + 1;
                    loadData(nowPage);
                }
                break;
            //重新刷新
            case R.id.loading:
                loading.setVisibility(View.GONE);
                loadData(nowPage);
            case R.id.back:
                finish();
                break;

        }
    }
}
