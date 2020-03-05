package com.example.myschool.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Description:
 * Date: 21:11 2019/12/9
 *
 * @author yong
 * @see
 */
public class BaseDocument {

    public static Document getDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36")
                    .cookie("JSESSIONID", RandomSessionId.sessionId)
                    .timeout(3000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.e("success","Get  "+ RandomSessionId.sessionId);
        return document;
    }

    public static Document postDocument(String url, Map<String, String> data) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36")
                    .cookie("JSESSIONID", RandomSessionId.sessionId)
                    .data(data)
                    .timeout(3000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.e("success","Post  "+ RandomSessionId.sessionId);
        return document;
    }
}
