package com.lyeye.dentalappointmentsystem.util;

public class UrlUtil {
    public static String URL;

//    192.168.42.65 手机
//    10.200.129.8 图书馆
public static String getURL(String s) {
    return new StringBuilder("http://10.200.129.8:8080/").append(s).toString();
}
}
