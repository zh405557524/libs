package com.soul.kotlin.basics

import android.util.Log
import android.view.View
import com.soul.lib.test.ButtonTextFragment

/**
 * Description: Lambda 表达式测试类
 * Author: 祝明
 * CreateDate: 2022/9/26 9:41
 * UpdateUser:
 * UpdateDate: 2022/9/26 9:41
 * UpdateRemark:
 */
class LambdaTestFragment : ButtonTextFragment(), View.OnClickListener {
    val TAG: String = "LambdaTestFragment"

    override fun getClassName(): String {
        return "Lambda 表达式测试类"
    }

    override fun initEvent() {
        addTextName("it", this)
    }

    override fun onClick(v: View?) {
        when (v?.getTag()) {

        }
    }
}