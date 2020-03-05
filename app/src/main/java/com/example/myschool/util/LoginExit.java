package com.example.myschool.util;

import org.jsoup.nodes.Document;

/**
 * 登出操作
 */
public class LoginExit {

    public String loginExit() {
        Document document = BaseDocument.getDocument(staticUrl.LOGINEXIT);
        if (document != null) {
            return document.text();
        }
        return null;
    }
}
