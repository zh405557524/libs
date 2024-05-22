package com.soul.lib.module.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.net.Uri;

import com.soul.lib.utils.LogUtil;

import static com.soul.lib.module.media.IMediaPlayer.OnMediaPlayerCallBackListener.error;
import static com.soul.lib.module.media.IMediaPlayer.OnMediaPlayerCallBackListener.succeed;


/**
 * Description:音频播放
 * Author: 祝明
 * CreateDate: 2019/12/4 13:37
 * UpdateUser:
 * UpdateDate: 2019/12/4 13:37
 * UpdateRemark:
 */
public class MediaPlayer implements IMediaPlayer {

    private volatile android.media.MediaPlayer mMediaPlayer;

    @Override
    public void play(Context context, Uri uri, final OnMediaPlayerCallBackListener listener) {
        try {
            cancel();
            mMediaPlayer = new android.media.MediaPlayer();
            mMediaPlayer.setDataSource(context, uri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(android.media.MediaPlayer mp) {
                    LogUtil.i("Tag", "MediaPlayer_播放完成");

                    if (listener != null) {
                        listener.onCompletion(succeed);
                    }
                    cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onCompletion(error);
            }
        }
    }

    /**
     * 播放在线音频
     *
     * @param context
     * @param url
     * @param listener
     */
    public void playSny(Context context, String url, final OnMediaPlayerCallBackListener listener) {
        try {
            cancel();
            mMediaPlayer = new android.media.MediaPlayer();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(android.media.MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });

            mMediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(android.media.MediaPlayer mp) {
                    LogUtil.i("Tag", "MediaPlayer_播放完成");

                    if (listener != null) {
                        listener.onCompletion(succeed);
                    }
                    cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onCompletion(error);
            }
        }
    }


    public void play(Context context, AssetFileDescriptor assetFileDescriptor, final OnMediaPlayerCallBackListener listener) {
        try {
            cancel();
            mMediaPlayer = new android.media.MediaPlayer();
            mMediaPlayer.setVolume(1.f, 1.f);
            mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(android.media.MediaPlayer mp) {
                    LogUtil.i("Tag", "MediaPlayer_播放完成");
                    cancel();
                    if (listener != null) {
                        listener.onCompletion(succeed);
                    }
                    if (mMediaPlayer != null) {
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onCompletion(error);
            }
        }
    }


    public void cancel() {
        try {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setVolume(float volume) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(volume, volume);
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void resume() {

        if (mMediaPlayer != null) {

        }
    }

    public void stop() {
        cancel();
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
