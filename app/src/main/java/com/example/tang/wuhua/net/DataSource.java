package com.example.tang.wuhua.net;

import android.support.annotation.StringRes;

/**
 * 数据源定义接口
 * @author z1ycheng
 */

public interface DataSource {

    /**
     * 只关注成功的接口
     * @param <T> 成功时返回的实体对象的类型
     */
    interface SuccessCallBack<T> {
        void onDataLoaded(T t);
    }

    /**
     * 只关注失败的接口
     */
    interface FailureCallBack {
        void onDataNotAvailable(@StringRes int str);
    }

    /**
     * 通知成功与失败的接口
     * @param <T> 成功时返回的实体对象的类型
     */
    interface CallBack<T> extends SuccessCallBack<T>, FailureCallBack {}

    /**
     * 销毁仓库操作
     */
    void dispose();
}
