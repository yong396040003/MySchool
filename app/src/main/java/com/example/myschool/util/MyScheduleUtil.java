package com.example.myschool.util;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:我的课表
 * Date: 21:00 2019/12/9
 *
 * @author yong
 * @see
 */
public class MyScheduleUtil {
    /**
     * 周数
     */
    private int zc;

    public MyScheduleUtil() {

    }

    public MyScheduleUtil(int zc) {
        this.zc = zc;
    }


    /**
     * 根据周数返回指定课表
     */
    public List<String> getMySchedule() {
        String url = staticUrl.MYSCHEDULE;
        if (zc != 0) {
            url = url + "?zc=" + this.zc;
        }
        Document document = BaseDocument.getDocument(url);
        if (document != null) {
            Elements table = document.getElementsByClass("kbcontent");
            //课表7x7
            List<String> strings = new ArrayList<>();
            //行
            int line = 0;
            for (Element text : table) {
                if (line == 35) {
                    break;
                }
                String[] a = text.text().split(" ");
                if (a.length > 2) {
                    strings.add(a[0] + "\n" + a[1] + "\n" + a[a.length - 1]);
                } else {
                    strings.add(text.text());
                }
                line++;
            }


            return strings;
        }
        return null;
    }
}
