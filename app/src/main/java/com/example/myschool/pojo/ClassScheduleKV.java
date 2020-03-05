package com.example.myschool.pojo;

import java.io.Serializable;

/**
 * Description:
 * Date: 0:05 2019/12/9
 *
 * @author yong
 * @see
 */
public class ClassScheduleKV implements Serializable {
    /**
     * 键
     */
    String key;

    /**
     * 值

     */
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ClassScheduleKV{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
