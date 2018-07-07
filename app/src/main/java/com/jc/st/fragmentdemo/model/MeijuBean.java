package com.jc.st.fragmentdemo.model;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/1/18.
 */

public class MeijuBean {
String meiju_href;
String meiju_imgurl;
String meiju_name;
String meiju_content;
String meijiu_time;
String meiju_jishu;
String nextpager;

    public String getMeiju_href() {
        return meiju_href;
    }

    public void setMeiju_href(String meiju_href) {
        this.meiju_href = meiju_href;
    }

    public String getMeiju_imgurl() {
        return meiju_imgurl;
    }

    public void setMeiju_imgurl(String meiju_imgurl) {
        this.meiju_imgurl = meiju_imgurl;
    }

    public String getMeiju_name() {
        return meiju_name;
    }

    public void setMeiju_name(String meiju_name) {
        this.meiju_name = meiju_name;
    }

    public String getMeiju_content() {
        return meiju_content;
    }

    public void setMeiju_content(String meiju_content) {
        this.meiju_content = meiju_content;
    }

    public String getMeijiu_time() {
        return meijiu_time;
    }

    public void setMeijiu_time(String meijiu_time) {
        this.meijiu_time = meijiu_time;
    }

    public String getMeiju_jishu() {
        return meiju_jishu;
    }

    public void setMeiju_jishu(String meiju_jishu) {
        this.meiju_jishu = meiju_jishu;
    }

    public String getNextpager() {
        return nextpager;
    }

    public void setNextpager(String nextpager) {
        this.nextpager = nextpager;
    }

    @Override
    public String toString() {
        return "MeijuBean{" +
                "meiju_href='" + meiju_href + '\'' +
                ", meiju_imgurl='" + meiju_imgurl + '\'' +
                ", meiju_name='" + meiju_name + '\'' +
                ", meiju_content='" + meiju_content + '\'' +
                ", meijiu_time='" + meijiu_time + '\'' +
                ", meiju_jishu='" + meiju_jishu + '\'' +
                ", nextpager='" + nextpager + '\'' +
                '}';
    }
}
