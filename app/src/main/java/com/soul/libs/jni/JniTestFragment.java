package com.soul.libs.jni;

import android.util.Log;
import android.view.View;

import com.soul.libs.test.ButtonTextFragment;

/**
 * Description: jni 测试类
 * Author: 祝明
 * CreateDate: 2020/2/19 16:07
 * UpdateUser:
 * UpdateDate: 2020/2/19 16:07
 * UpdateRemark:
 */
public class JniTestFragment extends ButtonTextFragment implements View.OnClickListener {
    @Override
    public String getClassName() {
        return "jni 测试类";
    }

    @Override
    protected void initEvent() {
        addTextName("demo测试", this);
        addTextName("启动服务端", this);
        addTextName("客户端发送数据", this);
        addTextName("c层获取java类String数据", this);

    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case "demo测试":
//                String s = JniTest.testDemo();
//                Log.i("Tag", "测试数据:" + s);
                break;
            case "启动服务端":
//                new Thread(JniTest::startUPDServer).start();
                break;
            case "客户端发送数据":
//                JniTest.sendClientData("123456");
                break;
            case "c层获取java类String数据":
                CallJavaTest test = new CallJavaTest();
                test.setData("hello");
                test.setDataSize(100);
                byte[] bytes = {'1', '2', '3', '4'};
                test.setByteData(bytes);
//                JniTest.sendCallJavaTest(test);
                Log.i("Tag", "native 设置 后的数值 data:" + test.getData() + " dataSize:" + test.getDataSize());
                break;
        }
    }
}
