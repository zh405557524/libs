package com.soul.lib.module.bluetooth;

import android.content.Context;

/**
 * Description: 蓝牙接口
 * Author: 祝明
 * CreateDate: 2021/5/10 10:27
 * UpdateUser:
 * UpdateDate: 2021/5/10 10:27
 * UpdateRemark:
 */
public interface IBluetoothManager {


    /**
     * 蓝牙是否打开
     *
     * @return true 已打开; false 未打开
     */
    boolean isOpen();

    /**
     * 打开蓝牙设置
     */
    void openSetting();

    /**
     * 搜索蓝牙设备
     */
    void searchDevice(Context context);

    /**
     * 获取蓝牙信息
     */
    void getDeviceInfo();

    /**
     * 选中设备
     */
    void chooseDevice();

    /**
     * 是否匹配
     *
     * @return true 已匹配; false 未匹配
     */
    boolean isMatch();

    /**
     * 进行蓝牙匹配
     */
    void matching();

    /**
     * 进行蓝牙连接
     */
    void connect();

    /**
     * 发送数据
     */
    void sendData();


}
