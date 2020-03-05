package com.example.myschool.util;

/**
 * Description:
 * Date: 21:19 2019/12/9
 *
 * @author yong
 * @see
 */
public class staticUrl {
    /**
     * 上课专业
     *
     */
    static String SKZY = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/kbcx/getZyByAjax";

    /**
     * 我的课表
     * 根据zc返回不同星系
     */
    static String MYSCHEDULE = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/xskb/xskb_list.do";

    /**
     * 登陆操作
     */
    public static String LOGIN = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/xk/LoginToXk";

    /**
     * 初始ip
     */
    static String INDEX = "http://54.222.196.251:81";

    /**
     * 查询所有的成绩
     */
    static String QUERYALLCJ = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/kscj/cjcx_list";
    /**
     * 退出
     */
    static String LOGINEXIT = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/xk/LoginToXk?method=exit";

    /**
     * 毕设选题
     * jsktmc: //课程名称
     * cxzdjs: //指导教师
     * pageIndex: //页数
     */
    static String SELECTTOPIC = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/bysj/xsxt.do";

    /**
     * 学籍预警
     */
    static String XJYJ = "http://54.222.196.251:81/cqdxcskjxy_jsxsd/xsxj/xsyjxx.do";
}
