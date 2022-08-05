package com.hirain.hirain.bean;

public class UdpBean {
    //路线
    public int route;
    //开启，暂停，接收
    public int type;
    //车速
    public String speed;
    //电量
    public String electric;

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }
}

