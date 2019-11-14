package com.ztx.study.client;

import com.ztx.study.comm.Clculator;
import com.ztx.study.comm.RPCRequest;
import com.ztx.study.tools.ZkTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClculatorRemoteImpl implements Clculator {
    @Override
    public int add(int a, int b) {
        ZkTools zkTools = new ZkTools();
        zkTools.createConnection();
        List<String> children = zkTools.getChildren("/ROOT/GoStudy/add_service_1.0");
        try {
            Socket socket = new Socket(children.get(0), 3333);
            RPCRequest rpcRequest = new RPCRequest();
            rpcRequest.setA(a);
            rpcRequest.setB(b);
            rpcRequest.setMethed("add");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            System.out.println(Thread.currentThread().getName()+"外调完成");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            if (object instanceof Integer) {
                return (Integer) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            zkTools.closeConnection();
        }
        return 0;
    }
}
