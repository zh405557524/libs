package com.soul.libs.jni;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2020/2/25 18:31
 * UpdateUser:
 * UpdateDate: 2020/2/25 18:31
 * UpdateRemark:
 */
public class CallJavaTest {

    private String data;

    private int dataSize;

    private byte[] byteData;

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }
}
