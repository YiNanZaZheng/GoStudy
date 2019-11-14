package com.ztx.study.client;

import com.ztx.study.tools.ZkTools;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MainClient {
    public static void main(String[] args) {
        new ClculatorRemoteImpl().add(21,1);
    }
}
