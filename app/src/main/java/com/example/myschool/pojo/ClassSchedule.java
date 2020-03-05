package com.example.myschool.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Date: 23:55 2019/12/8
 *
 * @author yong
 * @see
 */
public class ClassSchedule implements Serializable {
    /**
     * 主键
     */
    private long id;
    /**
     * 请求链接
     */
    String action;
    /**
     * 学年学期
     */
    List<ClassScheduleKV> xnxqh;
    /**
     *上课院系
     */
    List<ClassScheduleKV> skyx;
    /**
     * 上课年级
     */
    List<ClassScheduleKV> sknj;
    /**
     * 上课专业
     */
    List<ClassScheduleKV> skzy;
    /**
     * 周次1
     */
    List<ClassScheduleKV> zc1;
    /**
     * 周次2
     */
    List<ClassScheduleKV> zc2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ClassScheduleKV> getXnxqh() {
        return xnxqh;
    }

    public void setXnxqh(List<ClassScheduleKV> xnxqh) {
        this.xnxqh = xnxqh;
    }

    public List<ClassScheduleKV> getSkyx() {
        return skyx;
    }

    public void setSkyx(List<ClassScheduleKV> skyx) {
        this.skyx = skyx;
    }

    public List<ClassScheduleKV> getSknj() {
        return sknj;
    }

    public void setSknj(List<ClassScheduleKV> sknj) {
        this.sknj = sknj;
    }

    public List<ClassScheduleKV> getSkzy() {
        return skzy;
    }

    public void setSkzy(List<ClassScheduleKV> skzy) {
        this.skzy = skzy;
    }

    public List<ClassScheduleKV> getZc1() {
        return zc1;
    }

    public void setZc1(List<ClassScheduleKV> zc1) {
        this.zc1 = zc1;
    }

    public List<ClassScheduleKV> getZc2() {
        return zc2;
    }

    public void setZc2(List<ClassScheduleKV> zc2) {
        this.zc2 = zc2;
    }

    @Override
    public String toString() {
        return "ClassSchedule{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", xnxqh=" + xnxqh +
                ", skyx=" + skyx +
                ", sknj=" + sknj +
                ", skzy=" + skzy +
                ", zc1=" + zc1 +
                ", zc2=" + zc2 +
                '}';
    }
}
