package com.soul.lib.module.media;

import android.content.Context;
import android.net.Uri;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/12/4 13:36
 * UpdateUser:
 * UpdateDate: 2019/12/4 13:36
 * UpdateRemark:
 */
public interface IMediaPlayer {


    interface OnMediaPlayerCallBackListener {
        int succeed = 0;
        int error = 1;

        /**
         * 播放完成
         *
         * @param errorCode 0 成功 -1 失败
         */
        void onCompletion(int errorCode);
    }

    /**
     * 播放音频
     *
     * @param context
     * @param uri
     * @param listener
     */
    void play(Context context, Uri uri, OnMediaPlayerCallBackListener listener);

    /**
     * 取消播放
     */
    void cancel();

}
