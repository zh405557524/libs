package com.soul.libs.lib;

import android.media.AudioManager;
import android.util.Log;
import android.view.View;

import com.soul.libs.test.ButtonTextFragment;
import com.soul.lib.utils.LogUtils;
import com.soul.lib.utils.NetworkUtils;
import com.soul.libs.lib.test.B;
import com.soul.libs.lib.test.SystemManager;

/**
 * Description: TODO
 * Author: zhuMing
 * CreateDate: 2020/8/19 17:12
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/8/19 17:12
 * UpdateRemark:
 */
public class TestFragment extends ButtonTextFragment implements View.OnClickListener {


    @Override
    public String getClassName() {
        return "测试";
    }

    @Override
    protected void initEvent() {
        addTextName("字符串大小", this);
        addTextName("字符串方法", this);
        addTextName("类的继承", this);
        addTextName("获取音频焦点", this);
        addTextName("释放音频焦点", this);

        addTextName("网络连接判断", this);
        SystemManager.getInstance().init(getContext());


    }

    private String str = new String("text");

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case "字符串大小":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < 10000 * 100; i++) {
                            stringBuilder.append("一二三四五六七八九十");
                        }
                        String string = stringBuilder.toString();
                    }
                }).start();

                break;
            case "字符串方法":
                changeString(str);
                Log.i("Tag", "str:" + str);
                break;
            case "类的继承":
                B b = new B();
                break;
            case "获取音频焦点":
                boolean b1 = SystemManager.getInstance().requestAudioFocus(mOnAudioFocusChangeListener);
                LogUtils.i("Tag", "b1:" + b1);
                break;
            case "释放音频焦点":
                SystemManager.getInstance().abandonAudioFocus(mOnAudioFocusChangeListener);
                break;
            case "网络连接判断":
                boolean connected = NetworkUtils.isConnected();
                LogUtils.i("Tag", "网络连接判断:" + connected);
                break;
        }
    }

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

        }
    };


    private void changeString(String str) {
        str = "change";
    }


}
