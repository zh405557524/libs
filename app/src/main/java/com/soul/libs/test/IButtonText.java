package com.soul.libs.test;

import android.view.View;
import android.widget.Button;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-04 15:09
 * UpdateUser:
 * UpdateDate: 2019-07-04 15:09
 * UpdateRemark:
 */
public interface IButtonText {

    /**
     * 添加点击测试按钮
     *
     * @param clickName       点击按钮的名称
     * @param onClickListener 点击按钮的回调
     */
    Button addTextName(String clickName, View.OnClickListener onClickListener);

    //    TextView setText(String text);
    //文本显示框   打开(显示内容) 关闭 ，修改内容(覆盖，追加)


    //确认框，回调


    //文件管理器。


}
