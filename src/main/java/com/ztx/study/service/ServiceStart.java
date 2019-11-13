package com.ztx.study.service;

import com.ztx.study.zk.ZkTools;

import java.util.Date;

public class ServiceStart {
    public void regist(){
        ZkTools zkTools = new ZkTools();
        zkTools.createConnection();
        zkTools.deleteAll("/Test");
        zkTools.createNode("/Test/666",new Date()+"新节点");
        zkTools.updateData("/Test/666",new Date()+"更新数据");
        System.out.println(zkTools.readData("/Test/666"));
        zkTools.closeConnection();

    }
}
