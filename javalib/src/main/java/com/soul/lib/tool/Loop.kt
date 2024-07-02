package com.soul.lib.tool

import android.os.Handler
import com.soul.lib.Global

/**
 * Description: 循环
 * Author: 祝明
 * CreateDate: 2024/6/26 22:01
 * UpdateUser:
 * UpdateDate: 2024/6/26 22:01
 * UpdateRemark:
 */
class Loop {

    private var onLoopListener: OnLoopListener? = null
    private val handler = Handler(Global.getBackgroundHandler().looper)

    private var loopTime = 24L
    private var isLooping = false

    interface OnLoopListener {
        fun onLoop()
    }

    fun startLoop() {
        if (!isLooping) {
            isLooping = true
            loop()
        }
    }

    fun setOnLoopListener(listener: OnLoopListener) {
        onLoopListener = listener
    }

    fun setLoopTime(time: Long) {
        loopTime = time
    }

    fun stopLoop() {
        isLooping = false
        handler.removeCallbacks(loopTask)
    }

    private fun loop() {
        if (isLooping) {
            onLoopListener?.onLoop()
            handler.postDelayed(loopTask, loopTime)
        }
    }


    private val loopTask = Runnable { loop() }


}