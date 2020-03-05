package com.example.myschool.util;

import android.content.Context;
import android.util.Log;

import com.eclipsesource.v8.V8;
import com.example.myschool.pojo.Student;
import com.example.myschool.pojo.StudentUrl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:模拟登陆验证
 * Date: 18:04 2019/12/6
 *
 * @author yong
 * @see
 */
public class StudentLoginVerUtil {
    private Context context;

    public StudentLoginVerUtil(Context context) {
        this.context = context;
    }

    /**
     * 身份验证
     */
    public Map<String, Object> idVer(String url, String userName, String password) {
        Map<String, Object> map = new HashMap<>();
        Student student = null;
        String encoded = playJs(userName, password);
        Map<String, String> data = new HashMap<>(1);
        data.put("encoded", encoded);
        Document document = BaseDocument.postDocument(url, data);
        if (document != null) {
            Elements elements = document.getElementsByClass("dlmi");
            //判断账号密码是否错误
            String error = elements.select("font").text();
            if (!"".equals(error)) {
                map.put("msg", error);
                return map;
            } else if (document.getElementById("form122") != null) {
                map.put("msg", "你的密码安全系数过低，请去新教务系统更改密码");
                return map;
            }
            //获取用户所有信息
            student = getAllInfo(document);
            map.put("msg", "登陆成功");
            map.put("data", student);
        } else {
            map.put("msg", "网路连接失败");
        }
        return map;
    }

    /**
     * 获取用户所有的信息
     *
     * @param document
     */
    private Student getAllInfo(Document document) {
        Student student = new Student();
        List<StudentUrl> studentUrls = new ArrayList<>();
        //Nsb_menu menu_cn class下的所有数据
        Elements head = document.getElementsByClass("menu_cn");
        Elements headTitle = head.select("a[href]");
        for (Element element : headTitle) {
            StudentUrl studentUrl = new StudentUrl();
            String url = element.attr("href");
            String name = element.text();
            if ("".equals(name)) {
                name = "首页";
            }
            studentUrl.setName(name);
            studentUrl.setUrl(url);
            studentUrls.add(studentUrl);
        }

        //个人信息（姓名 学号）
        Elements personalInfo = document.getElementsByClass("block1text");
        String[] t = personalInfo.text().replaceAll("姓名：", "").replaceAll("学号：", "").split(" ");
        student.setName(t[0]);
        student.setNumber(Integer.parseInt(t[1]));

        //获取wap class的所有信息
        Elements elements = document.getElementsByClass("wap");
        //获取wap class里带有href的a标签
        Elements a = elements.select("a[href]");
        for (Element element : a) {
            StudentUrl studentUrl = new StudentUrl();
            String url = element.attr("href");
            String name = element.text();
            studentUrl.setName(name);
            studentUrl.setUrl(url);
            studentUrls.add(studentUrl);
        }
        student.setStudentUrlList(studentUrls);
        return student;
    }

    /**
     * Java执行JavaScript脚本破解加密算法
     *
     * @param userName
     * @param password
     * @return 加密后的字符串
     */
    private String playJs(String userName, String password) {
        String result = null;
        try {
            InputStream is = context.getAssets().open("conwork.js");   //获取用户名与密码加密的js代码
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                V8 runtime = V8.createV8Runtime();      //使用J2V8运行js代码并将编码结果返回
                final String encodename = runtime.executeStringScript(sb.toString()
                        + "encodeInp('" + userName + "');\n");
                final String encodepwd = runtime.executeStringScript(sb.toString() + "encodeInp('" + password + "');\n");
                runtime.release();
                result = encodename + "%%%" + encodepwd;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
