package com.umeng.soexample.zk1demo.presenter;

import com.umeng.soexample.zk1demo.callback.MyCallBack;
import com.umeng.soexample.zk1demo.iview.Iview;
import com.umeng.soexample.zk1demo.model.ModelImpl;

import java.util.Map;

/**
 * Created by W on 2019/2/16 9:41.
 */

public class PresenterImpl implements Presenter {
    private ModelImpl model;
    private Iview iview;
    public PresenterImpl(Iview iview){
        this.iview = iview;
        model = new ModelImpl();
    }
    @Override
    public void get(String url, Map<String, String> map, Map<String, String> headmap, Class kind) {
        model.get(url, map, headmap, kind, new MyCallBack() {
            @Override
            public void setSuccess(Object success) {
                iview.success(success);
            }

            @Override
            public void setError(Object error) {
                iview.error(error);
            }
        });
    }
}
