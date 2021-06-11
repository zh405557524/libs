package com.soul.lib.module.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.util.Log;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * Description: ble蓝牙连接数据处理中心
 * Author: 祝明
 * CreateDate: 2021/6/11 18:11
 * UpdateUser:
 * UpdateDate: 2021/6/11 18:11
 * UpdateRemark:
 */
public class BluetoothBleProcessorManager implements BluetoothBleConnectManager.OnBluetoothBleConnectListener {

    public static String TAG = BluetoothBleProcessorManager.class.getSimpleName();

    private final BluetoothBleConnectManager mBluetoothBleConnectManager;
    private List<BluetoothGattCharacteristic> mCharacteristics;

    public BluetoothBleProcessorManager() {
        mBluetoothBleConnectManager = new BluetoothBleConnectManager();
        mBluetoothBleConnectManager.setOnBluetoothBleConnectListener(this);
    }


    /**
     * 开始连接
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void startConnect(Activity activity) {
        //第一步 搜索
        mBluetoothBleConnectManager.search(activity);
    }


    @Override
    public void onScanDevice(BluetoothDevice device) {
        if (matchBleAddress(device)) {//蓝牙设备匹配成功，则进行连接
            mBluetoothBleConnectManager.connect(device);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onServices(List<BluetoothGattService> services) {
        BluetoothGattService bluetoothGattService = matchServices(services);
        if (bluetoothGattService != null) {
            mCharacteristics = bluetoothGattService.getCharacteristics();
            //发送ble认证
            sendCertification();
        }
    }


    /**
     * 发送ble认证
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void sendCertification() {
        Log.d(TAG, "发送认证");
        //        this.mToken[0] = (new Random()).nextInt(99);
        //        this.mToken[1] = (new Random()).nextInt(99);
        //        final int t1 = this.mToken[0];
        //        final int t2 = this.mToken[1];
        //        int index = 1;
        //        ArrayList<String> uuids = new ArrayList();
        //        if (this.mBluetoothGatt != null) {
        //            this.callbackMsg("BLE发送认证");
        //            Iterator var5 = this.mBluetoothGattCharacteristics.iterator();
        //
        //            while (true) {
        //                BluetoothGattCharacteristic characteristic;
        //                boolean enabled;
        //                do {
        //                    if (!var5.hasNext()) {
        //                        return;
        //                    }
        //
        //                    characteristic = (BluetoothGattCharacteristic) var5.next();
        //                    this.callbackMsg("BluetoothGattService characteristic :" + characteristic.getUuid().toString());
        //                    if (uuids.contains(characteristic.getUuid().toString())) {
        //                        return;
        //                    }
        //
        //                    uuids.add(characteristic.getUuid().toString());
        //                    enabled = this.mBluetoothGatt.setCharacteristicNotification(characteristic, true);
        //                } while (!enabled);
        //
        //                characteristic.setWriteType(2);
        //
        //                for (Iterator var8 = characteristic.getDescriptors().iterator(); var8.hasNext(); ++index) {
        //                    final BluetoothGattDescriptor dp = (BluetoothGattDescriptor) var8.next();
        //                    if ((characteristic.getProperties() & 16) != 0) {
        //                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        //                    } else if ((characteristic.getProperties() & 32) != 0) {
        //                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        //                    }
        //
        //                    final BluetoothGattCharacteristic finalCharacteristic = characteristic;
        //                    this.postRunnable(new Runnable() {
        //                        @SuppressLint({"NewApi"})
        //                        public void run() {
        //                            if (BleManager.this.mBluetoothGatt != null) {
        //                                Log.e("写描述结果", BleManager.this.mBluetoothGatt.writeDescriptor(dp) + " BluetoothGattDescriptor:" + dp.getUuid());
        //                            }
        //
        //                            if (finalCharacteristic.getDescriptors().indexOf(dp) == finalCharacteristic.getDescriptors().size() - 1 && finalCharacteristic.getUuid().toString().contains("000010b1")) {
        //                                BleManager.this.sendToDevice("000010b1", new byte[]{10, 1}, new byte[]{com.centaurstech.bluetooth.ByteUtil.hexToByte(Integer.toHexString(t1)), com.centaurstech.bluetooth.ByteUtil.hexToByte(Integer.toHexString(t2))});
        //                            }
        //
        //                        }
        //                    }, index * 200);
        //                }
        //            }
        //        } else {
        //            if (this.mBleConnectListener != null) {
        //                this.mBleConnectListener.onLeScan(new ArrayList(), com.centaurstech.bluetooth.BluetoothConnectState.SCAN_ERROR);
        //            }
        //
        //            this.mBluetoothConnectState = com.centaurstech.bluetooth.BluetoothConnectState.BLE_UNCONNECTED;
        //        }
    }


    /**
     * 匹配蓝牙服务
     *
     * @param services
     * @return
     */
    private BluetoothGattService matchServices(List<BluetoothGattService> services) {

        return null;
    }


    /**
     * 匹配ble蓝牙耳机地址
     */
    private boolean matchBleAddress(BluetoothDevice device) {
        String[] ad1 = device.getAddress().split(":");
        String[] ad2 = device.getAddress().split(":");
        if (ad1.length > 2 && ad2.length > 2 && ad1[0].equals(ad2[0]) && ad1[1].equals(ad2[1])) {
            return true;
        }
        return false;
    }

}
