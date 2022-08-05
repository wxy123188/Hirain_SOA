package com.hirain.hirain.bean.event;

public class EditModeEvent {
    public  String modeName;
    public  int editState;  // 0 开始编辑  1 保存编辑  2 取消编辑  3 另存新模式  4 新建新模式 5开启新模式


    public EditModeEvent(String modeName, int editState) {
        this.modeName = modeName;
        this.editState = editState;
    }

    public int getEditState() {
        return editState;
    }

    public void setEditState(int editState) {
        this.editState = editState;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
}
