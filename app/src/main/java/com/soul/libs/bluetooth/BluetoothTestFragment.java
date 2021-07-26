package com.soul.libs.bluetooth;

import android.os.Build;
import android.view.View;

import com.soul.lib.module.bluetooth.BluetoothProcessorManager;
import com.soul.lib.test.ButtonTextFragment;

import androidx.annotation.RequiresApi;

/**
 * Description: 蓝牙测试类
 * Author: 祝明
 * CreateDate: 2021/5/10 10:05
 * UpdateUser:
 * UpdateDate: 2021/5/10 10:05
 * UpdateRemark:
 */
public class BluetoothTestFragment extends ButtonTextFragment implements View.OnClickListener {

    private BluetoothProcessorManager mBluetoothBleProcessorManager;

    @Override
    public String getClassName() {
        return "蓝牙测试类";
    }

    @Override
    protected void initEvent() {
        //1、判断是否连接
        //2、打开蓝牙连接
        //3、搜索蓝牙设备
        //4、获取配对信息
        //5、选择蓝牙设备
        //6、判断是否配对
        //7、进行配对
        //8、进行连接
        //9、传输数据
        addTextName("开始蓝牙ble连接", this);

        mBluetoothBleProcessorManager = new BluetoothProcessorManager();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case "开始蓝牙ble连接":
                mBluetoothBleProcessorManager.startConnect(getActivity());
                break;
        }
    }
}
