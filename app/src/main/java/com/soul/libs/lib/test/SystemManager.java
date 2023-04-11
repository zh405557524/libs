package com.soul.libs.lib.test;

import android.content.Context;
import android.media.AudioManager;

/**
 * Description: 音频焦点管理类
 * Author: 祝明
 * CreateDate: 2020/9/2 11:28
 * UpdateUser:
 * UpdateDate: 2020/9/2 11:28
 * UpdateRemark:
 */
public class SystemManager {


    private AudioManager audioManager;

    public static SystemManager getInstance() {
        return SystemMangerHold.sInstance;
    }


    public void init(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    public boolean requestAudioFocus(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        if (audioManager == null) {
            return false;
        }
        boolean isFocus = false;
        int ret = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC |
                AudioManager.STREAM_ALARM |
                AudioManager.STREAM_VOICE_CALL |
                        AudioManager.STREAM_ACCESSIBILITY,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);
        isFocus = ret == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        return isFocus;
    }

    public boolean abandonAudioFocus(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        if (audioManager == null) {
            return false;
        }
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        return false;
    }


    public boolean mute() {
        if (audioManager == null) {
            return false;
        }
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        return true;
    }

    public boolean cancelMute() {
        if (audioManager == null) {
            return false;
        }
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        return true;
    }


    private static class SystemMangerHold {
        private static SystemManager sInstance = new SystemManager();
    }
}

