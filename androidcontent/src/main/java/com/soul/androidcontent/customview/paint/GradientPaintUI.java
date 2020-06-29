package com.soul.androidcontent.customview.paint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;

import com.soul.androidcontent.R;
import com.soul.androidcontent.customview.CustomUI;
import com.soul.lib.utils.LogUtils;

import static com.soul.lib.utils.UIUtils.getResources;

/**
 * Description: PaintAPI集合
 * Author: zhuMing
 * CreateDate: 2020/6/29 17:57
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/6/29 17:57
 * UpdateRemark:
 */
public class GradientPaintUI implements CustomUI {


    /**
     *   setColor(Color.RED);//设置颜色
     *   setARGB(255, 255, 255, 0);//设置paint对象颜色，范围0~255
     *   setAlpha(200);//设置alpha 不透明，范围0~255
     *   setAntiAlias(true);//抗锯齿
     *   setStyle(Paint.Style.STROKE);//描边效果 FILL 填充;STROKE 描边; FILL_AND_STROKE 填充并表变
     *   setStrokeWidth(4);//描边宽度
     *   setStrokeCap(Paint.Cap.ROUND);//圆角效果 BUTT 默认; ROUND 圆角;SQUARE 方形
     *   setStrokeJoin(Paint.Join.MITER);//拐角风格 MITER 尖角;ROUND 切除尖角;BEVEL 圆角
     */

    /**
     * 画笔
     */
    private final Paint mPaint;
    private final Shader mShader;
    private final Bitmap mBitmap;
    /**
     * 线性渲染
     */
    private final LinearGradient mLinearGradient;

    /**
     * 环形渲染
     */
    private final RadialGradient mRadialGradient;

    /***
     * 扫描渲染
     */
    private final SweepGradient mSweepGradient;

    public GradientPaintUI() {
        //获取bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        //创建画笔
        mPaint = new Paint();
        //1、设置颜色
        mPaint.setColor(Color.RED);
        //2、设置透明度
        mPaint.setAlpha(200);
        //3、设置抗锯齿
        mPaint.setAntiAlias(true);
        //4.1、设置描边效果，为fill填充
        mPaint.setStyle(Paint.Style.FILL);
        //5、设置描边宽度为4
        mPaint.setStrokeWidth(10);

        //1、线性渲染
        mLinearGradient = new LinearGradient(0, 0, 250, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.1f, 0.5f, 1f}, Shader.TileMode.CLAMP);
        //2 环形渲染
        mRadialGradient = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED},
                null, Shader.TileMode.CLAMP);

        //3 扫描渲染
        mSweepGradient = new SweepGradient(250, 250, Color.RED, Color.GREEN);

        //4 位图渲染
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        //        mShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);

        //5 组合渲染
        BitmapShader bitmapShaderCompose = new BitmapShader(mBitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradientCompose = new LinearGradient(0, 0, 1000, 16000, new
                int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.CLAMP);

        mShader = new ComposeShader(bitmapShaderCompose, linearGradientCompose, PorterDuff.Mode.MULTIPLY);

    }


    @Override
    public void onDraw(Canvas canvas) {
        LogUtils.i("Tag", "onDraw");
        canvas.save();
        canvas.translate(100, 100);
        canvas.drawCircle(0, 0, 20, mPaint);
        canvas.translate(0, 100);

        //4.2、设置描边效果，为STROKE描边
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, 20, mPaint);
        canvas.translate(0, 100);

        //4.3、设置描边效果，为FILL_AND_STROKE 填充并表变
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(0, 0, 20, mPaint);
        canvas.translate(100, 0);

        canvas.restore();
    }

}

