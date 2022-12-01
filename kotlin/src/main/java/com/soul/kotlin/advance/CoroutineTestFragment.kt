package com.soul.kotlin.advance

import android.view.View
import com.soul.lib.test.ButtonTextFragment
import com.soul.lib.utils.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Description: 协程测试类
 * Author: 祝明
 * CreateDate: 2022/12/1 17:23
 * UpdateUser:
 * UpdateDate: 2022/12/1 17:23
 * UpdateRemark:
 */
class CoroutineTestFragment : ButtonTextFragment(), View.OnClickListener {
    val TAG: String = "CoroutineTestFragment"
    override fun getClassName(): String {
        return "协程测试类"
    }

    override fun initEvent() {
        addTextName("launch ", this)
    }

    override fun onClick(v: View?) {
        when (v?.tag) {
            "launch" -> launch()
        }
    }

    private fun launch() {
        GlobalScope.launch {
            LogUtils.i(TAG, "launch 1")
        }

        GlobalScope.launch {
            LogUtils.i(TAG, "launch 2")
        }
    }
}