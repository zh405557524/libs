package com.soul.lib.module.media;

import android.media.MediaPlayer;


import com.soul.lib.utils.LogUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * Description: android 原生播放器
 * Author: 祝明
 * CreateDate: 2023/10/19 16:53
 * UpdateUser:
 * UpdateDate: 2023/10/19 16:53
 * UpdateRemark:
 */
public class Player implements IPlayer {

    private static final String TAG = "Player";
    /**
     * 空闲状态
     */
    public static final int STATE_IDLE = 0;
    /**
     * 播放完成
     */
    public static final int STATE_ENDED = 1;
    /**
     * 缓冲中
     */
    public static final int STATE_BUFFERING = 2;
    /**
     * 准备就绪
     */
    public static final int STATE_READY = 3;
    private MediaPlayer mMediaPlayer;

    private final List<Listener> mListenerList = new ArrayList<>();

    public Player() {
        mMediaPlayer = initMediaPlayer();
    }

    boolean isPrepare = false;

    public void setVolume(float v, float v1) {
        mMediaPlayer.setVolume(v, v1);
    }

    public void setLooping(boolean b) {
        mMediaPlayer.setLooping(b);
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }


    /**
     * 播放状态
     */
    private enum PlayState {
        NONE,//默认状态
        PLAY,//开始播放
        LOADING,//加载中
        PAUSE, //暂停
        COMPLETE, //完成
        STOP//停止
    }


    public volatile PlayState playState = PlayState.NONE;

    @NonNull
    private MediaPlayer initMediaPlayer() {
        playState = PlayState.NONE;
        final MediaPlayer mediaPlayer;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (playState == PlayState.PLAY) {
                    for (Listener listener : mListenerList) {
                        listener.onPlaybackStateChanged(STATE_ENDED);
                    }
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepare = false;
                for (Listener listener : mListenerList) {
                    listener.onPlaybackStateChanged(STATE_READY);
                }
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                LogUtil.i(TAG, "onError 播放出错");
                mMediaPlayer = initMediaPlayer();
                for (Listener listener : mListenerList) {
                    listener.onPlaybackStateChanged(STATE_ENDED);
                }
                return false;
            }
        });
        return mediaPlayer;
    }

    @Override
    public void addListener(Listener listener) {
        if (!mListenerList.contains(listener) && listener != null) {
            mListenerList.add(listener);
        }
    }

    @Override
    public synchronized void setMediaSource(String mediaSource) throws RuntimeException {
        isPrepare = true;
        playState = PlayState.LOADING;
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        } else {
            mMediaPlayer = initMediaPlayer();
        }

        try {
            for (Listener listener : mListenerList) {
                listener.onPlaybackStateChanged(STATE_BUFFERING);
            }
            mMediaPlayer.setDataSource(mediaSource);
        } catch (Exception e) {
            e.printStackTrace();
            mMediaPlayer = initMediaPlayer();
            LogUtil.i(TAG, "播放出错");
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void prepare() {
        try {
            if (playState != PlayState.STOP && playState != PlayState.PAUSE) {
                LogUtil.i(TAG, "开始准备播放");
                mMediaPlayer.prepareAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void play() {
        LogUtil.i(TAG, "play");
        try {
            if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                LogUtil.i(TAG, "realPlay");
                mMediaPlayer.start();
                playState = PlayState.PLAY;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(long l) {
        if (isPrepare) {
            return;
        }
        try {
            mMediaPlayer.seekTo((int) l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void pause() {
        playState = PlayState.PAUSE;
        if (isPrepare) {
            return;
        }
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        playState = PlayState.STOP;
        if (isPrepare) {
            return;
        }
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized long getCurrentPosition() {
        if (isPrepare) {
            return 0;
        }
        try {

            return mMediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public synchronized long getDuration() {
        if (isPrepare) {
            return 0;
        }
        try {
            return mMediaPlayer.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
