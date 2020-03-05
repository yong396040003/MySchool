package com.example.myschool.activity.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.example.myschool.R;
import com.example.myschool.pojo.CJ;
import com.example.myschool.util.QueryCjUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 成绩
 */
public class CjFragment extends Fragment {

    @Bind(R.id.table)
    SmartTable table;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.error)
    TextView error;
    @Bind(R.id.common)
    Button common;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.renovate)
    ImageView renovate;

    private ProgressDialog dialog = null;
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
                Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
            } else {
                final List<CJ> cjs = (List<CJ>) msg.obj;
                table.setData(cjs);
                table.getConfig().setShowTableTitle(false);
                table.getConfig().setShowXSequence(false);
                table.getConfig().setShowYSequence(false);
                table.setZoom(true);

                //设置隔行显示不同颜色
                ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
                    @Override
                    public int getBackGroundColor(CellInfo cellInfo) {
                        if (cellInfo.col == 9) {
                            if ("素质".equals(cellInfo.value)) {
                                return ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorEditText_true);
                            }
                        }
                        return TableConfig.INVALID_COLOR;
                    }

                    @Override
                    public int getTextColor(CellInfo cellInfo) {
                        if (cellInfo.col == 4) {
                            String pattern = "^[1-9]\\d*$";
                            //如果不是root用户则需判断账号是否为纯数字，是否合法
                            boolean isNumber = Pattern.matches(pattern, cellInfo.value);
                            if (isNumber) {
                                int score = Integer.parseInt(cellInfo.value);
                                if (score < 60) {
                                    return ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent);
                                }
                            }
                        }
                        return super.getTextColor(cellInfo);
                    }
                };

                table.getConfig().setContentCellBackgroundFormat(backgroundFormat);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cj, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        loadData();
    }

    private void initView() {
        common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.GONE);

                loadData();
            }
        });
        renovate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    /**
     * 获取所有成绩
     *
     * @return
     */
    private void loadData() {
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
                message.obj = new QueryCjUtil().getAllCj();
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        initData();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
