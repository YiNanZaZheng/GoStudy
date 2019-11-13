package com.ztx.study.rpc;

import java.io.Serializable;

public class RPCRequest implements Serializable {
    private int a;
    private int b;
    private String methed;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getMethed() {
        return methed;
    }

    public void setMethed(String methed) {
        this.methed = methed;
    }

    @Override
    public String toString() {
        return "RPCRequest{" +
                "a=" + a +
                ", b=" + b +
                ", methed='" + methed + '\'' +
                '}';
    }
}
