package com.soul.basekotlinui

import androidx.viewbinding.ViewBinding

/**
 * Description: ui基类
 * Author: 祝明
 * CreateDate: 2024/5/30 11:38
 * UpdateUser:
 * UpdateDate: 2024/5/30 11:38
 * UpdateRemark:
 */
abstract class BaseUI<T : ViewBinding>(val viewBinding: T) {

    init {
        this.initView()
        this.initEvent()
    }

    abstract fun initView()

    abstract fun initEvent()
}