package com.example.myschool;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            int year = 2019;
            datet = simpleDateFormat.parse(year+"-9-1");
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        // 天数。
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.YEAR);


        if (w < 0)
            w = 0;

        Date date = new Date();
        assert datet != null;
        long t = (date.getTime() - datet.getTime()) / (1000 * 3600 * 24) / 7 + 1;
        System.out.println(t);
    }
}