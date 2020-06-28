package com.soul.kotlin.basics

import android.view.View
import com.soul.lib.test.ButtonTextFragment

/**
 * Description: kotlin基础测试类
 * Author: zhuMing
 * CreateDate: 2020/6/28 18:53
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/6/28 18:53
 * UpdateRemark:
 */
public class BasicsTestFragment : ButtonTextFragment(), View.OnClickListener {


    override fun getClassName(): String {
        return "kotlin基础"
    }

    override fun initEvent() {
        addTextName("基础语法", this)
    }


    override fun onClick(v: View?) {

        when (v?.getTag()) {
            "基础语法" -> {
                Grammar().main(null)
            }
        }

    }

}