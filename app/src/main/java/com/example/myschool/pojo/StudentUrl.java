package com.example.myschool.pojo;

import java.io.Serializable;

/**
 * Description:
 * Date: 18:56 2019/12/7
 *
 * @author yong
 * @see
 */
public class StudentUrl implements Serializable {
    /**
     * 链接名
     */
    private String name;
    /**
     * 链接
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StudentUrl{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
