package com.soul.libs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import java.lang.Math.cos
import java.lang.Math.sin

/**
 * Description: TODO
 * Author: 祝明
 * CreateDate: 2023/6/17 21:27
 * UpdateUser:
 * UpdateDate: 2023/6/17 21:27
 * UpdateRemark:
 */
class RotatingImageView : View {


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    @SuppressLint("CustomViewStyleable", "Recycle")
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {

    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap? = null
    private var angle = 0f

    // 图片资源ID
    private val imageResId = R.mipmap.card

    init {
        bitmap = BitmapFactory.decodeResource(resources, imageResId)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // 计算图片的位置
        val radius = Math.min(centerX, centerY) * 0.6f
        val x = centerX + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        val y = centerY + radius * sin(Math.toRadians(angle.toDouble())).toFloat()

        // 绘制图片

        if (bitmap != null) {
            canvas.drawBitmap(bitmap!!, x - bitmap!!.width / 2, y - bitmap!!.height / 2, paint)
        }
    }

    fun startRotating() {
        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                angle = interpolatedTime * 360
                invalidate()
            }
        }
        anim.duration = 3000 // 动画时长，你可以根据需要调整
        anim.repeatCount = Animation.INFINITE
        startAnimation(anim)
    }
}