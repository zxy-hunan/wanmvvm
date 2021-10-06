package com.zyx_hunan.wanmvvm.compose.video;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zyx_hunan.wanmvvm.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerItemNormalHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context;

    SampleVideo gsyVideoPlayer;

    ImageView imageView;

    TextView textitle, textdesc;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public RecyclerItemNormalHolder(Context context, View v) {
        super(v);
        this.context = context;
        gsyVideoPlayer = v.findViewById(R.id.video_item_player);

        textitle = v.findViewById(R.id.textView7);
        textdesc = v.findViewById(R.id.textView8);
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void onBind(final int position, VideoModel videoModel) {

        String url;
        String title;
        url = videoModel.getPlayUrl();
        title = videoModel.getTitle();

        textitle.setText(title);
        textdesc.setText(videoModel.getDescription());



        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
//                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(false);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);


        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        //gsyVideoPlayer.loadCoverImageBy(R.mipmap.xxx2, R.mipmap.xxx2);
        gsyVideoPlayer.loadCoverImage(videoModel.getFeed(),R.mipmap.xxx1);
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    public SampleVideo getPlayer() {
        return gsyVideoPlayer;
    }
}
