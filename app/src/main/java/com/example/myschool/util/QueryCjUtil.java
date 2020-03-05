package com.example.myschool.util;

import android.util.Log;

import com.example.myschool.pojo.CJ;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询成绩
 */
public class QueryCjUtil {

    /**
     * 查询所有的成绩
     *
     * @return
     */
    public List<CJ> getAllCj() {
        Document document = BaseDocument.getDocument(staticUrl.QUERYALLCJ);
        if (document != null) {
            List<CJ> cjs = new ArrayList<>();
            Elements table = document.select("table[id]");
            Elements tr = table.select("tr");
            int i = 0;
            for (Element text : tr) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] cj = text.text().split(" ");
                cjs.add(format(cj));
            }
            return cjs;
        } else {
            System.out.println("数据加载异常");
        }

        return null;
    }

    private CJ format(String[] cj) {
        CJ c = new CJ();
        for (int i = 0; i < cj.length; i++) {
            switch (i) {
                case 0:
                    c.setId(Integer.parseInt(cj[i]));
                    break;
                case 1:
                    c.setData(cj[i]);
                    break;
                case 2:
                    c.setNum(cj[i]);
                    break;
                case 3:
                    c.setName(cj[i]);
                    break;
                case 4:
                    c.setScore(cj[i]);
                    break;
                case 5:
                    c.setCredit(Float.parseFloat(cj[i]));
                    break;
                case 6:
                    c.setTotalClassHours(Integer.parseInt(cj[i]));
                    break;
                case 7:
                    c.setGpa(Float.parseFloat(cj[i]));
                    break;
                case 8:
                    c.setExaMode(cj[i]);
                    break;
                case 9:
                    c.setTextBook(cj[i]);
                    break;
                case 10:
                    c.setCurriculumNature(cj[i]);
                    break;
            }
        }
        return c;
    }
}
