package com.example.myschool.util;

import android.util.Log;

import com.example.myschool.pojo.XJYJ;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 书籍预警加载数据
 */
public class StudentWarning {
    //显示总页数和条数
    private String pageTotal = null;

    public List<XJYJ> studentWarning(int page) {
        if (page == 0) {
            page = 1;
        }
        //传参数据
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", String.valueOf(page));
        Document document = BaseDocument.getDocument(staticUrl.XJYJ);

        if (document != null) {
            Elements table = document.getElementsByClass("Nsb_r_list Nsb_table");
            pageTotal = document.getElementsByClass("Nsb_r_list_fy3").text();
            Elements tr = table.select("tr");
            //毕设选题列表
            List<XJYJ> xjyjList = new ArrayList<>();
            //去除表最上面一行
            int i = 0;
            for (Element element : tr) {
                if (i == 0) {
                    i++;
                    continue;
                }
                Elements td = element.select("td");
                xjyjList.add(format(td));
            }
            return xjyjList;
        } else {
            Log.e("success", "毕设选题加载数据失败");
        }
        return null;
    }

    /**
     * 获取总页数和条数
     *
     * @return
     */
    public String pageTotal() {
        return pageTotal;
    }

    /**
     * 学籍预警格式化
     *
     * @param td
     * @return
     */
    private XJYJ format(Elements td) {
        XJYJ xjyj = new XJYJ();
        int i = 0;
        for (Element text : td) {
            switch (i) {
                case 0:
                    xjyj.setId(Integer.parseInt(text.text()));
                    break;
                case 1:
                    xjyj.setDate(text.text());
                    break;
                case 2:
                    xjyj.setName(text.text());
                    break;
                case 3:
                    xjyj.setRequirement(text.text());
                    break;
                case 4:
                    xjyj.setProResult(text.text());
                    break;
                case 5:
                    xjyj.setMsg(text.text());
                    break;
                case 6:
                    xjyj.setObject(text.text());
                    break;
                case 7:
                    xjyj.setNum(Integer.parseInt(text.text()));
                    break;
            }
            i++;
        }
        return xjyj;
    }
}
