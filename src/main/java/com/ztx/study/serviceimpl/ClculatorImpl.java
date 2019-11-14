package com.ztx.study.serviceimpl;

import com.ztx.study.comm.Clculator;
import com.ztx.study.comm.RPCRequest;
import com.ztx.study.tools.ZkTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ClculatorImpl implements Clculator {

    public void start(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ZkTools zkTools = new ZkTools();
        zkTools.createConnection();
        zkTools.deleteAll();
        zkTools.createNode("/ROOT/GoStudy/add_service_1.0/" + address.getHostAddress(), null);
        zkTools.closeConnection();
        waitLink();
    }

    public void waitLink(){
        try {
            ServerSocket serverSocket = new ServerSocket(3333);
            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println(Thread.currentThread().getName()+"收到请求");
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();
                    int result = 0;
                    if (object instanceof RPCRequest) {
                        RPCRequest rpcRequest = (RPCRequest) object;
                        if ("add".equals(rpcRequest.getMethed())) {
                            result=add(rpcRequest.getA(),rpcRequest.getB());
                        }else {
                            System.out.println(Thread.currentThread().getName()+"未开放的功能");
                        }
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(new Integer(result));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}