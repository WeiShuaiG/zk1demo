package com.umeng.soexample.zk1demo.iview;

/**
 * Created by W on 2019/2/16 9:32.
 */

public interface Iview<T> {
    void success(T success);
    void error(T error);
}
