package com.xxyoung.myone.bean;

import java.util.List;

/**
 * Created by Xxyou on 2017/2/28.
 */

public class IdListBean {
    /**
     * res : 0
     * data : ["3739","3769","3735","3734","3733","3732","3705","3730","3721","3714"]
     */

    private int res;
    private List<String> data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
