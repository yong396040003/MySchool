package com.example.myschool.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.example.myschool.R;
import com.example.myschool.pojo.BSXT;
import com.example.myschool.util.LoadDataBSXT;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MySelectTopicActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.ktmc)
    TextView ktmc;
    @Bind(R.id.ktmc1)
    EditText ktmc1;
    @Bind(R.id.ktmc_L)
    LinearLayout ktmcL;
    @Bind(R.id.zdjs)
    TextView zdjs;
    @Bind(R.id.zdjs1)
    EditText zdjs1;
    @Bind(R.id.zdjs_L)
    LinearLayout zdjsL;
    @Bind(R.id.submit_topic)
    Button submitTopic;
    @Bind(R.id.query)
    RelativeLayout query;
    @Bind(R.id.table)
    SmartTable<BSXT> table;
    @Bind(R.id.error)
    TextView error;
    @Bind(R.id.common)
    Button common;
    @Bind(R.id.loading)
    RelativeLayout loading;
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

    private LoadDataBSXT loadDataBSXT;

    /**
     * 加载框
     */
    private ProgressDialog dialog = null;

    //课程名称
    private String name;
    //指导教师
    private String teacher1;
    //当前页数
    private int nowPage = 1;

    //尾页
    private int endPage = 100000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
            if (msg.obj == null) {
                Toast.makeText(MySelectTopicActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
            } else {
                final List<BSXT> bsxtList = (List<BSXT>) msg.obj;
                //初始化data数据
                initTableData(bsxtList);

                //隐藏顶部标题名
                table.getConfig().setShowTableTitle(false);
                //隐藏左右两侧
                table.getConfig().setShowXSequence(false);
                table.getConfig().setShowYSequence(false);
                //可放大缩小
                table.setZoom(false);
                //隔行显示不同颜色
                tableSetBackground(table);

                tableB.setVisibility(View.VISIBLE);
                //设置总页数和总行数
                pageTotal.setText(loadDataBSXT.pageTotal());
                String end = loadDataBSXT.pageTotal().split(" ")[0].replace("共", "").replace("页", "");
                endPage = Integer.parseInt(end);
                page.setText(String.valueOf(nowPage));
            }
        }
    };


    //@SmartColumn(id = 0,name = "年度")
    private Column<String> year;
    //@SmartColumn(id=1,name = "题目")
    private Column<String> title;
    //@SmartColumn(id = 2,name = "院系")
    private Column<String> faculty;
    //@SmartColumn(id = 3,name = "指导老师")
    private Column<String> teacher;
    //@SmartColumn(id = 4,name = "职称")
    private Column<String> posi;
    //@SmartColumn(id=5,name = "已选人数")
    private Column<String> selectNum;
    //@SmartColumn(id = 6,name = "可报人数上限")
    private Column<String> upperPeople;

    private void initTableData(List<BSXT> bsxtList) {
        //初始化列
        title = new Column<>("题目", "title");
        teacher = new Column<>("指导老师", "teacher");
        selectNum = new Column<>("已选", "selectNum");
        upperPeople = new Column<>("上限", "upperPeople");

        setOnClickListener(title, bsxtList);
        setOnClickListener(teacher, bsxtList);
        setOnClickListener(selectNum, bsxtList);
        setOnClickListener(upperPeople, bsxtList);

        TableData<BSXT> tableData = new TableData<>("毕设选题", bsxtList, title, teacher, selectNum, upperPeople);
        table.setTableData(tableData);
    }

    /**
     * 每一列加点击监听
     *
     * @param title
     * @param bsxtList
     */
    private void setOnClickListener(final Column<String> title, final List<BSXT> bsxtList) {
        title.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String value, String s, int position) {
                Toast.makeText(MySelectTopicActivity.this, bsxtList.get(position).toString(), Toast.LENGTH_SHORT).show();


                table.refreshDrawableState();
                table.invalidate();
            }
        });
    }

    /**
     * table隔行显示不同颜色
     *
     * @param table
     */
    private void tableSetBackground(SmartTable<BSXT> table) {
        //设置隔行显示不同颜色
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return ContextCompat.getColor(MySelectTopicActivity.this, R.color.my_bg);
                }
                return TableConfig.INVALID_COLOR;
            }
        };

        table.getConfig().setContentCellBackgroundFormat(backgroundFormat);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_selected_topic;
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initView() {
        //查询提交
        submitTopic.setOnClickListener(this);
        //返回
        back.setOnClickListener(this);
        //上一页
        pre.setOnClickListener(this);
        //下一页
        next.setOnClickListener(this);
        //根据页数跳转
        page.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String p = v.getText().toString().trim();
                    hideInput();
                    int p1 = Integer.parseInt(p);
                    if (p1 >= endPage) {
                        Toast.makeText(MySelectTopicActivity.this, "超出最大页数", Toast.LENGTH_SHORT).show();
                        page.setText(String.valueOf(nowPage));
                    } else {
                        nowPage = p1;
                        loadData(name, teacher1, nowPage);
                    }
                    return true;
                }
                return false;
            }
        });
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
    }

    /**
     * 加载数据
     *
     * @param name
     * @param teacher
     * @param page
     */
    public void loadData(final String name, final String teacher, final int page) {
        if (dialog == null) {
            dialog = ProgressDialog.show(
                    MySelectTopicActivity.this,
                    "毕设选题", "加载中...");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                loadDataBSXT = new LoadDataBSXT();
                message.obj = loadDataBSXT.loadData(name, teacher, page);
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //查询
            case R.id.submit_topic:
                hideInput();
                name = ktmc1.getText().toString();
                teacher1 = zdjs1.getText().toString();
                loadData(name, teacher1, nowPage);
                break;
            //重新加载
            case R.id.common:
                loading.setVisibility(View.GONE);
                loadData(name, teacher1, nowPage);
                break;
            //上一页
            case R.id.pre:
                if (nowPage <= 1) {
                    Toast.makeText(MySelectTopicActivity.this, "当前已是第一页", Toast.LENGTH_SHORT).show();
                } else {
                    nowPage = nowPage - 1;
                    loadData(name, teacher1, nowPage);
                }
                break;
            //下一页
            case R.id.next:
                if (nowPage == endPage) {
                    Toast.makeText(MySelectTopicActivity.this, "当前已是最后一页", Toast.LENGTH_SHORT).show();
                } else {
                    nowPage = nowPage + 1;
                    loadData(name, teacher1, nowPage);
                }
                break;
            case R.id.back:
                finish();
                break;

        }
    }
}
