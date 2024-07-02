package com.soul.lib.wigth

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView
import com.soul.lib.R

/**
 * Description: 带圆角的ImageView
 * Author: 祝明
 * CreateDate: 2023/4/13 18:11
 * UpdateUser:
 * UpdateDate: 2023/4/13 18:11
 * UpdateRemark:
 */
class RoundedImageView : androidx.appcompat.widget.AppCompatImageView {
    private var rightBottomRadius: Int = 0
    private var leftBottomRadius: Int = 0
    private var rightTopRadius: Int = 0
    private var leftTopRadius: Int = 0
    private var radius: Int = 8

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    @SuppressLint("CustomViewStyleable", "Recycle")
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.RadiusImageView)
        radius =
            obtainStyledAttributes.getDimension(
                R.styleable.RadiusImageView_circle_radius,
                radius.toFloat()
            ).toInt()
        if (radius != 0) {
            leftTopRadius = radius
            rightTopRadius = radius;
            leftBottomRadius = radius;
            rightBottomRadius = radius;
        }
        obtainStyledAttributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        // 保证图片宽高大于圆角宽高， 获取圆角的宽高
        // 取横着大的长度
        val maxLeft = Math.max(leftTopRadius, leftBottomRadius)
        val maxRight = Math.max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        // 取竖着大的长度
        val maxTop = Math.max(leftTopRadius, rightTopRadius)
        val maxBottom = Math.max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width > minWidth && height > minHeight) {
            val path = Path()
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius.toFloat(), 0F)

            path.lineTo((width - rightTopRadius).toFloat(), 0F)
            path.quadTo(width.toFloat(), 0F, width.toFloat(), rightTopRadius.toFloat())

            path.lineTo(width.toFloat(), (height - rightBottomRadius).toFloat())
            path.quadTo(
                width.toFloat(),
                height.toFloat(),
                (width - rightBottomRadius).toFloat(),
                height.toFloat()
            )

            path.lineTo(leftBottomRadius.toFloat(), height.toFloat())
            path.quadTo(0F, height.toFloat(), 0F, (height - leftBottomRadius).toFloat())

            path.lineTo(0F, leftTopRadius.toFloat())
            path.quadTo(0F, 0F, leftTopRadius.toFloat(), 0F)

            canvas!!.clipPath(path)
        }
        super.onDraw(canvas)
    }
}