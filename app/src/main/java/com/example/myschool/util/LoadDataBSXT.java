package com.example.myschool.util;

import android.util.Log;

import com.example.myschool.pojo.BSXT;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadDataBSXT {
    //显示总页数和条数
    private String pageTotal = null;

    /**
     * 加载数据
     *
     * @param name    //课程名
     * @param teacher //老师
     * @param page    //页数
     */
    public List<BSXT> loadData(String name, String teacher, int page) {
        if (page == 0) {
            page = 1;
        }
        //传参数据
        Map<String, String> map = new HashMap<>();
        map.put("jsktmc", name);
        map.put("cxzdjs", teacher);
        map.put("pageIndex", String.valueOf(page));
        Document document = BaseDocument.postDocument(staticUrl.SELECTTOPIC, map);

        if (document != null) {
            Elements table = document.getElementsByClass("Nsb_r_list Nsb_table");
            pageTotal = document.getElementsByClass("Nsb_r_list_fy3").text();
            Elements tr = table.select("tr");
            //毕设选题列表
            List<BSXT> bsxtList = new ArrayList<>();
            //去除表最上面一行
            int i = 0;
            for (Element element : tr) {
                if (i == 0) {
                    i++;
                    continue;
                }
                Elements td = element.select("td");
                bsxtList.add(format(td));
            }
            return bsxtList;
        } else {
            Log.e("success", "毕设选题加载数据失败");
        }
        return null;
    }

    /**
     * 获取总页数和条数
     * @return
     */
    public String pageTotal() {
        return pageTotal;
    }

    /**
     * 毕设选题格式化
     *
     * @param td
     * @return
     */
    private BSXT format(Elements td) {
        BSXT bsxt = new BSXT();
        int i = 0;
        for (Element text : td) {
            switch (i) {
                case 0:
                    bsxt.setYear(text.text());
                    break;
                case 1:
                    bsxt.setTitle(text.text());
                    break;
                case 2:
                    bsxt.setFaculty(text.text());
                    break;
                case 3:
                    bsxt.setTeacher(text.text());
                    break;
                case 4:
                    bsxt.setPosi(text.text());
                    break;
                case 5:
                    bsxt.setSelectNum(text.text());
                    break;
                case 6:
                    bsxt.setUpperPeople(text.text());
                    break;
            }
            i++;
        }
        return bsxt;
    }
}
