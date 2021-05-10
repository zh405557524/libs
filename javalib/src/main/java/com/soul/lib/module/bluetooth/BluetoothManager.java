package com.soul.lib.module.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Description: 蓝牙管理类
 * Author: 祝明
 * CreateDate: 2021/5/10 10:27
 * UpdateUser:
 * UpdateDate: 2021/5/10 10:27
 * UpdateRemark:
 * <p> 需要如下权限
 * <uses-permission android:name="android.permission.BLUETOOTH"/>
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
 */
public class BluetoothManager implements IBluetoothManager {


    private static BluetoothManager sBluetoothManager;

    public static BluetoothManager getInstance() {
        if (sBluetoothManager == null) {
            synchronized (BluetoothManager.class) {
                if (sBluetoothManager == null) {
                    sBluetoothManager = new BluetoothManager();
                }
            }
        }
        return sBluetoothManager;
    }

    private BluetoothManager() {


    }

    @Override
    public boolean isOpen() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            return defaultAdapter.isEnabled();
        }
        return false;
    }

    @Override
    public void openSetting() {
        //todo 打开蓝牙开关设置
    }

    @Override
    public void searchDevice(Context context) {

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        IntentFilter filter1 = new IntentFilter();
    }

    @Override
    public void getDeviceInfo() {

    }

    @Override
    public void chooseDevice() {

    }

    @Override
    public boolean isMatch() {
        return false;
    }

    @Override
    public void matching() {

    }

    @Override
    public void connect() {

    }

    @Override
    public void sendData() {

    }
}
