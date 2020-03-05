package com.example.myschool.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * 用于随机生成一个sessionId
 *
 * @author yong
 * @time 2019/12/17 15:07
 */
public class RandomSessionId {
    private Context context;
    private RandomSessionId randomSessionId;

    public RandomSessionId getInstances(Context context) {
        if (randomSessionId == null) {
            randomSessionId = new RandomSessionId(context);
        }
        return randomSessionId;
    }

    public RandomSessionId(Context context) {
        this.context = context;
    }

    static String sessionId = "DDD8026A08B135B4C6C1652AD7BF62C1";


    /**
     * 随机生成一个session id
     */
    public void makeSessionId() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sessionId.length(); i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("sessionId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", sb.toString());
        editor.apply();

        sessionId = sb.toString();
    }

    public String getSessionId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sessionId", Context.MODE_PRIVATE);
        sessionId = sharedPreferences.getString("session", "DDD8026A08B135B4C6C1652AD7BF62C1");
        return sessionId;
    }
}
