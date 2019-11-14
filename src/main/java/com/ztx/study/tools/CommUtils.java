package com.ztx.study.tools;

public class CommUtils {
    public static String nvl(String a, String defalt) {
        if (a == null) {
            return defalt;
        }else {
            return a;
        }
    }
}
