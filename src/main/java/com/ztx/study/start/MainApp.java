package com.ztx.study.start;

import com.ztx.study.service.ServiceStart;
import com.ztx.study.zk.ZkTools;

public class MainApp {
    public static void main(String[] args) {
        new ServiceStart().regist();
    }
}
