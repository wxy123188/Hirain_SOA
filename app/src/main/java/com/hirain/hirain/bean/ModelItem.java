package com.hirain.hirain.bean;

public class ModelItem {
    //自定义模式名字
    public String modeName;
    //是否选中
    public boolean isSel;

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
