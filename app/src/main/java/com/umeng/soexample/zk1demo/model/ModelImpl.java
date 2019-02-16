package com.umeng.soexample.zk1demo.model;

import com.google.gson.Gson;
import com.umeng.soexample.zk1demo.callback.MyCallBack;
import com.umeng.soexample.zk1demo.network.RetrofitUtils;

import java.util.Map;

/**
 * Created by W on 2019/2/16 9:35.
 */

public class ModelImpl implements Model {
    @Override
    public void get(String url, Map<String, String> map, Map<String, String> headmap, final Class kind, final MyCallBack callBack) {
        RetrofitUtils.getInstance().get(url,map,headmap).setHttpListener(new RetrofitUtils.HttpListener() {
            @Override
            public void onSuccess(String jsonStr) {
                Gson gson = new Gson();
                Object s = gson.fromJson(jsonStr,kind);
                callBack.setSuccess(s);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
