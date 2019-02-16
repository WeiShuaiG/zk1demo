package com.umeng.soexample.zk1demo.callback;

/**
 * Created by W on 2019/2/16 9:31.
 */

public interface MyCallBack<T> {
    void setSuccess(T success);
    void setError(T error);
}
