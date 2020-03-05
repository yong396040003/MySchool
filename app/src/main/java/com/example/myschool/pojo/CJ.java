package com.example.myschool.pojo;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.io.Serializable;

/**
 * 成绩实体类
 * 序号 开课学期 课程编号
 * 课程名称 成绩 学分 总学时
 * 绩点 考核方式 课程属性 课程性质
 */
@SmartTable(name = "成绩")
public class CJ implements Serializable {
    @SmartColumn(id = 0,name = "序号")
    private int id;
    @SmartColumn(id = 1,name = "开课学期",autoMerge = true)
    private String data;
    @SmartColumn(id = 2,name = "课程编号",fixed = true)
    private String num;
    @SmartColumn(id = 3,name = "课程名称",autoMerge = true)
    private String name;
    @SmartColumn(id = 4,name = "成绩")
    private String score;
    @SmartColumn(id = 5,name = "学分")
    private float credit;
    @SmartColumn(id = 6,name = "总学时")
    private int totalClassHours;
    @SmartColumn(id = 7,name = "绩点")
    private float Gpa;
    @SmartColumn(id = 8,name = "考核方式",autoMerge = true)
    private String ExaMode;
    @SmartColumn(id = 9,name = "课程属性",autoMerge = true)
    private String textBook;
    @SmartColumn(id = 10,name = "课程性质",autoMerge = true)
    private String CurriculumNature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public int getTotalClassHours() {
        return totalClassHours;
    }

    public void setTotalClassHours(int totalClassHours) {
        this.totalClassHours = totalClassHours;
    }

    public float getGpa() {
        return Gpa;
    }

    public void setGpa(float gpa) {
        Gpa = gpa;
    }

    public String getExaMode() {
        return ExaMode;
    }

    public void setExaMode(String exaMode) {
        ExaMode = exaMode;
    }

    public String getTextBook() {
        return textBook;
    }

    public void setTextBook(String textBook) {
        this.textBook = textBook;
    }

    public String getCurriculumNature() {
        return CurriculumNature;
    }

    public void setCurriculumNature(String curriculumNature) {
        CurriculumNature = curriculumNature;
    }
}
