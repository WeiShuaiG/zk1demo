package com.umeng.soexample.zk1demo.model;

import com.umeng.soexample.zk1demo.callback.MyCallBack;

import java.util.Map;

/**
 * Created by W on 2019/2/16 9:34.
 */

public interface Model {
    void get(String url, Map<String,String> map, Map<String,String> headmap, Class kind, MyCallBack callBack);
}
