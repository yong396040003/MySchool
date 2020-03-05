package com.example.myschool.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myschool.pojo.ClassSchedule;
import com.example.myschool.pojo.ClassScheduleKV;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Description:班级课表
 * Date: 18:46 2019/12/8
 *
 * @author yong
 * @see
 */
public class ClassScheduleUtil {

    /**
     * 查询班级课表
     *
     * @param url
     */
    public ClassSchedule queryClassSchedule(String url) {
        ClassSchedule classSchedule = null;
        Document document = BaseDocument.getDocument(url);
        if (document != null) {
            classSchedule = new ClassSchedule();
            Element element = document.getElementById("Form1");
            //请求链接
            String action = element.attr("action");
            //学年学期
            List<ClassScheduleKV> xnxqh = new ArrayList<>();
            //上课院系
            List<ClassScheduleKV> skyx = new ArrayList<>();
            //上课年级
            List<ClassScheduleKV> sknj = new ArrayList<>();
            //周次1
            List<ClassScheduleKV> zc1 = new ArrayList<>();
            //周次2
            List<ClassScheduleKV> zc2 = new ArrayList<>();
            int i = 0;
            Elements elements = element.select("select[name]");
            for (Element element1 : elements) {
                Elements elements1 = element1.select("option");
                for (Element element2 : elements1) {
                    ClassScheduleKV classScheduleKV = new ClassScheduleKV();
                    if ("-请选择-".equals(element2.text())) {
                        i++;
                        continue;
                    }
                    classScheduleKV.setKey(element2.text());
                    classScheduleKV.setValue(element2.attr("value"));
                    if (i == 0) {
                        xnxqh.add(classScheduleKV);
                    } else if (i == 1) {
                        skyx.add(classScheduleKV);
                    } else if (i == 2) {
                        sknj.add(classScheduleKV);
                    } else if (i == 4) {
                        zc1.add(classScheduleKV);
                    } else if (i == 5) {
                        zc2.add(classScheduleKV);
                    } else {
                        break;
                    }
                }
            }
            classSchedule.setAction(action);
            classSchedule.setXnxqh(xnxqh);
            classSchedule.setSknj(sknj);
            classSchedule.setSkyx(skyx);
            classSchedule.setZc1(zc1);
            classSchedule.setZc2(zc2);
        }
        return classSchedule;
    }

    /**
     * 获取上课专业
     * 只有通过上课院系和上课年级来判断专业
     *
     * @param skyx
     * @param sknj
     * @return
     */
    private List<ClassScheduleKV> getSKZY(String skyx, String sknj) {
        Document document;
        String url = staticUrl.SKZY + "?skyx=" + skyx + "&sknj=" + sknj;
        document = BaseDocument.getDocument(url);
        if (document != null) {
            List<ClassScheduleKV> classScheduleKVS = new ArrayList<>();
            Element body = document.body();
            JSONArray array = JSONArray.parseArray(body.text());
            for (int i = 0; i < array.size(); i++) {
                ClassScheduleKV classScheduleKV = new ClassScheduleKV();
                JSONObject jsonObject = array.getJSONObject(i);
                classScheduleKV.setKey(jsonObject.getString("dmmc"));
                classScheduleKV.setValue(jsonObject.getString("dm"));
                classScheduleKVS.add(classScheduleKV);
            }
            return classScheduleKVS;
        }
        return null;
    }
}
