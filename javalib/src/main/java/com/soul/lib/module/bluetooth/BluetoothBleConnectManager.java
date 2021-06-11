package com.soul.lib.module.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.soul.lib.utils.UIUtils;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * Description: 蓝牙4.0连接管理
 * Author: 祝明
 * CreateDate: 2021/6/10 18:23
 * UpdateUser:
 * UpdateDate: 2021/6/10 18:23
 * UpdateRemark:
 */
public class BluetoothBleConnectManager implements IBluetoothBleConnectManager, BluetoothBleManager.OnBluetoothConnectListener {

    public static String TAG = BluetoothBleConnectManager.class.getSimpleName();
    /**
     * 搜索蓝牙设备
     */
    private static final int BLUE_SEARCH = 0;

    /**
     * 连接蓝牙设备
     */
    private static final int BLUE_CONNECT = 1;

    /**
     * 断开蓝牙连接
     */
    private static final int BLUE_DISCONNECT = 2;


    private final Handler mHandler;

    /**
     * 当前将要连接的蓝牙设备
     */
    private BluetoothDevice currentDevice;

    /**
     * 启动循环连接蓝牙
     */
    private boolean isStatLoopConnect;


    interface OnBluetoothBleConnectListener {
        /**
         * 搜索的设备回调
         */
        void onScanDevice(BluetoothDevice device);

        /**
         * 获取蓝牙服务成功
         *
         * @param services 蓝牙服务
         */
        void onServices(List<BluetoothGattService> services);
    }

    private OnBluetoothBleConnectListener mOnBluetoothBleConnectListener;


    public BluetoothBleConnectManager() {
        HandlerThread blue_connect_thread = new HandlerThread("blue_connect_thread");
        blue_connect_thread.start();
        //为了避免多线程引起的连接状态错乱，故用handler 保证所有操作在同一线程
        mHandler = new Handler(blue_connect_thread.getLooper()) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                onHandleMessage(msg);
            }
        };
        BluetoothBleManager.getInstance().setOnBluetoothConnectListener(this);
    }

    public void setOnBluetoothBleConnectListener(OnBluetoothBleConnectListener onBluetoothBleConnectListener) {
        mOnBluetoothBleConnectListener = onBluetoothBleConnectListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void search(Activity activity) {
        do {
            if (!BluetoothBleManager.getInstance().isOpen()) { //判断蓝牙是否打开
                BluetoothBleManager.getInstance().openSetting(activity);
                break;
            }
            sendMessage(BLUE_SEARCH);
        } while (false);
    }

    @Override
    public void connect(BluetoothDevice device) {
        sendMessage(BLUE_CONNECT, device);
    }

    @Override
    public void disConnect() {
        sendMessage(BLUE_DISCONNECT);
    }

    /**------------------------------蓝牙连接 回调 start--------------------------------*/

    /**
     * 设备搜索结果
     *
     * @param device
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onScanDevice(BluetoothDevice device) {

    }

    /**
     * 已成功连接蓝牙设备
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onConnected() {
        stopLoopConnect();
    }

    /**
     * 连接断开
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDisConnected() {
        if (isStatLoopConnect) {
            startLoopConnect();
        }
    }

    @Override
    public void onServices(List<BluetoothGattService> services) {

    }

    /**------------------------------蓝牙连接 回调 end--------------------------------*/

    /**
     * 发送handler消息
     *
     * @param what
     */
    private void sendMessage(int what) {
        sendMessage(what, null);
    }

    private void sendMessage(int what, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = what;
        obtain.obj = obj;
        mHandler.sendMessage(obtain);
    }

    /**
     * handler 消息处理
     *
     * @param msg
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void onHandleMessage(Message msg) {
        switch (msg.what) {
            case BLUE_SEARCH://1、搜索
            {
                BluetoothBleManager.getInstance().searchDevice();
            }
            break;
            case BLUE_CONNECT://2、连接
            {
                if (msg.obj instanceof BluetoothDevice) {
                    currentDevice = (BluetoothDevice) msg.obj;
                    isStatLoopConnect = true;
                    startLoopConnect();
                    Log.i(TAG, "开始连接蓝牙设备");
                    break;
                }

            }
            break;
            case BLUE_DISCONNECT://3、断开连接
            {
                stopLoopConnect();
                BluetoothBleManager.getInstance().disConnect();
            }
            break;
        }
    }

    /***
     * 启动循环连接蓝牙
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startLoopConnect() {
        mHandler.post(connectTask);
    }

    /**
     * 停止循环连接蓝牙
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void stopLoopConnect() {
        mHandler.removeCallbacks(connectTask);
    }

    /**
     * ble蓝牙连接任务
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private Runnable connectTask = new Runnable() {
        @Override
        public void run() {
            BluetoothBleManager.getInstance().connect(UIUtils.getContext(), currentDevice);
            //2分钟之内 如果无法连接成功，则重新连接
            mHandler.postDelayed(connectTask, 1000 * 60 * 2);
        }
    };


}
