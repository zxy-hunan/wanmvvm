package com.zyx_hunan.wanmvvm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zyx_hunan.baseutil.net.util.MyObserver;
import com.zyx_hunan.wanmvvm.R;
import com.zyx_hunan.wanmvvm.logic.model.BannerModel;

public class TestAcy extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acy_test);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestUtil.bannerList(TestAcy.this, new MyObserver<BannerModel>(TestAcy.this) {
                    @Override
                    public void onSuccess(BannerModel result) {
                        Log.i("test","result:"+result.toString());
                        Log.i("test","result:"+result.getErrorMsg()+" "+result.getErrorCode()+" "+result.getData().size());
                        Toast.makeText(TestAcy.this, ""+result.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }

                    @Override
                    public void onComplete(Boolean isError) {

                    }
                });
            }
        });
    }
}
