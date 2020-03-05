package com.example.myschool.pojo;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.io.Serializable;

/**
 * 学籍预警
 */
@SmartTable(name = "学籍预警")
public class XJYJ implements Serializable {
    @SmartColumn(id = 0, name = "序号")
    private int id;
    @SmartColumn(id = 1, name = "预警学期")
    private String date;
    @SmartColumn(id = 2, name = "预警名称")
    private String name;
    @SmartColumn(id = 3, name = "预警条件")
    private String requirement;
    @SmartColumn(id = 4, name = "处理结果")
    private String proResult;
    @SmartColumn(id = 5, name = "提示信息")
    private String msg;
    @SmartColumn(id = 6, name = "对象名称")
    private String object;
    @SmartColumn(id = 7, name = "实际值")
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getProResult() {
        return proResult;
    }

    public void setProResult(String proResult) {
        this.proResult = proResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
