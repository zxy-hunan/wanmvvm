package com.zyx_hunan.wanmvvm.ui;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zyx_hunan.baseutil.net.util.MyObserver;
import com.zyx_hunan.baseutil.net.util.NetUtil;
import com.zyx_hunan.baseutil.net.util.RxHelper;
import com.zyx_hunan.wanmvvm.logic.model.BannerModel;
import com.zyx_hunan.wanmvvm.logic.net.TestService;

/**
 * 请求示例工具类
 */
public class TestUtil {

        public static void bannerList(RxAppCompatActivity context, MyObserver<BannerModel> observer){
        TestService testService= (TestService) NetUtil.getApiPath();
            testService.bannerList()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

}
