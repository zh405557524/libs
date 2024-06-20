package com.soul.basekotlinui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.soul.lib.utils.Bar2Utils

/**
 * Description: kotlinActivity的基类
 * Author: zhuMing
 * CreateDate: 2024-05-17 16:56
 * ProjectName: traveler
 * UpdateUser:
 * UpdateDate: 2024-05-17 16:56
 * UpdateRemark:
 */
abstract class KotlinBaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //沉浸状态至布局中
        transparentStatusBar()
        //设置状态栏的色彩模式为Light
        setStatusBarLightMode()
        viewBinding = getRootViewBind()
        setContentView(viewBinding.root)
        initView()
        initData()
        initEvent()
        initObserver()
    }

    open fun setStatusBarLightMode() {
        Bar2Utils.setStatusBarLightMode(this, true)
    }

    open fun transparentStatusBar() {
        Bar2Utils.transparentStatusBar(this)
    }

    abstract fun getRootViewBind(): T

    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()

    open fun initObserver() {

    }
}