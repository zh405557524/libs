package com.qiwu.app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet

/**
 * Description: 颜色渐变的字体
 * Author: 祝明
 * CreateDate: 2023/6/14 16:04
 * UpdateUser:
 * UpdateDate: 2023/6/14 16:04
 * UpdateRemark:
 */
class RankingGradientTextView : androidx.appcompat.widget.AppCompatTextView {

    //可用xy的值，控制方向
    private var linearGradient = LinearGradient(
        0f, 0f, width.toFloat(), 0f,
        intArrayOf(
            Color.parseColor("#FF0D0240"),
            Color.parseColor("#FF2738C0")
        ), floatArrayOf(0.3f, 1F), Shader.TileMode.CLAMP
    )


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    @SuppressLint("CustomViewStyleable", "Recycle")
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        initGradient()
    }


    override fun onDraw(canvas: Canvas) {
        paint.shader = linearGradient
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
// 在这里设置渐变的宽度，确保是从左到右
        linearGradient = LinearGradient(
            0f, 0f, w.toFloat(), 0f,
            intArrayOf(Color.parseColor("#FF0D0240"), Color.parseColor("#FF2738C0")),
            floatArrayOf(0.3f, 0.7f), Shader.TileMode.CLAMP
        )
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        initGradient()  // 更新渐变以适应新的文本大小

    }

    private fun initGradient() {
        // 初始化渐变。实际的渐变参数将在onSizeChanged中设置。
        linearGradient = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            intArrayOf(Color.parseColor("#FF0D0240"), Color.parseColor("#FF2738C0")),
            floatArrayOf(0.3f, 0.7f), Shader.TileMode.CLAMP
        )
    }


}