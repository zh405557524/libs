package com.soul.basekotlinui

import android.view.View

/**
 * Description: ui基类
 * Author: 祝明
 * CreateDate: 2024/5/30 11:38
 * UpdateUser:
 * UpdateDate: 2024/5/30 11:38
 * UpdateRemark:
 */
abstract class BaseViewUI(val viewRoot: View) {

    init {
        viewRoot.post {
            this.initView(viewRoot)
            this.initEvent()
        }
    }

    abstract fun initView(viewRoot: View)

    abstract fun initEvent()
}