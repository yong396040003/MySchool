package com.example.myschool.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Date: 18:53 2019/12/7
 *
 * @author yong
 * @see
 */
public class Student implements Serializable {
    /**
     * 主键
     */
    private long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private int number;
    /**
     * 其对应的各个链接
     */
    private List<StudentUrl> studentUrlList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<StudentUrl> getStudentUrlList() {
        return studentUrlList;
    }

    public void setStudentUrlList(List<StudentUrl> studentUrlList) {
        this.studentUrlList = studentUrlList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", studentUrlList=" + studentUrlList +
                '}';
    }
}
