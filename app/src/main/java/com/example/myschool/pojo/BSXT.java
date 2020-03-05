package com.example.myschool.pojo;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.io.Serializable;

/**
 * 毕设选题
 *
 */
//@SmartTable(name = "毕设选题")
public class BSXT implements Serializable {
    //@SmartColumn(id = 0,name = "年度")
    private String year;
    //@SmartColumn(id=1,name = "题目")
    private String title;
    //@SmartColumn(id = 2,name = "院系")
    private String faculty;
    //@SmartColumn(id = 3,name = "指导老师")
    private String teacher;
    //@SmartColumn(id = 4,name = "职称")
    private String posi;
    //@SmartColumn(id=5,name = "已选人数")
    private String selectNum;
    //@SmartColumn(id = 6,name = "可报人数上限")
    private String upperPeople;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPosi() {
        return posi;
    }

    public void setPosi(String posi) {
        this.posi = posi;
    }

    public String getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(String selectNum) {
        this.selectNum = selectNum;
    }

    public String getUpperPeople() {
        return upperPeople;
    }

    public void setUpperPeople(String upperPeople) {
        this.upperPeople = upperPeople;
    }

    @Override
    public String toString() {
        return "BSXT{" +
                "year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", faculty='" + faculty + '\'' +
                ", teacher='" + teacher + '\'' +
                ", posi='" + posi + '\'' +
                ", selectNum='" + selectNum + '\'' +
                ", upperPeople='" + upperPeople + '\'' +
                '}';
    }
}
