package com.soul.kotlin

import android.app.Application
import com.soul.lib.base.AppInstanceBase

/**
 * Description: TODO
 * Author: zhuMing
 * CreateDate: 2020/6/28 20:00
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/6/28 20:00
 * UpdateRemark:
 */
 class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val application: AppInstanceBase = AppInstanceBase()
        application.onCreate(this)

    }
}