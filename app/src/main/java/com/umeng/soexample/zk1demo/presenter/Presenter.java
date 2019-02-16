package com.umeng.soexample.zk1demo.presenter;

import java.util.Map;

/**
 * Created by W on 2019/2/16 9:40.
 */

public interface Presenter {
    void get(String url, Map<String,String> map,Map<String,String> headmap,Class kind);
}
