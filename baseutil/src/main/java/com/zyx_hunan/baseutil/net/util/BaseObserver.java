package com.zyx_hunan.baseutil.net.util;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 数据返回统一处理  参考https://www.jianshu.com/p/ff619fea7e22
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    @Override
    public void onNext(BaseResponse<T> response) {
        Log.e(TAG, "BaseObserver onNext");
        //在这边对 基础数据 进行统一处理  举个例子：
        if(response.getCode()==0){
            if (response.getEntity()!=null) {
                onSuccess(response.getEntity());
            }else{
                onSuccess(response);
            }
        }else{
            onFailure(null,response.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {//服务器错误信息处理
        Log.e(TAG, "BaseObserver onError");
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
        onComplete(true);
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "BaseObserver onComplete");
        onComplete(false);
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.e(TAG, "BaseObserver onSubscribe");
    }

    public abstract void onSuccess(T result);

    public abstract void onSuccess(BaseResponse response);

    public abstract void onFailure(Throwable e,String errorMsg);

    public abstract void onComplete(Boolean isError);

}
