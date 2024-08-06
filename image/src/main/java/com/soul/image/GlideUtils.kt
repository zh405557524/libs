package com.soul.image

import android.annotation.SuppressLint
import android.media.Image
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.soul.lib.Global
import com.soul.lib.utils.LogUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Description: Glide工具类
 * Author: 祝明
 * CreateDate: 2024/8/6 15:35
 * UpdateUser:
 * UpdateDate: 2024/8/6 15:35
 * UpdateRemark:
 */
class GlideUtils {

    @SuppressLint("CheckResult")
    fun loadImg(url: String,
                imageView: ImageView, radius: Float = 0F, defaultRes: Int = R.drawable.bg_radius_d6_ffc4c4c4) {
        loadImage(Uri.parse(url), imageView, radius, defaultRes)
    }

    @SuppressLint("CheckResult")
    fun loadImg(res: Int,
                imageView: ImageView, radius: Float = 0F, defaultRes: Int = R.drawable.bg_radius_d6_ffc4c4c4) {
        loadImage(null, imageView, radius, res = res, defaultRes = defaultRes)
    }

    private fun loadImage(
        uri: Uri?, //资源地址
        imageView: ImageView,
        radius: Float = 0F, //圆角
        defaultRes: Int = R.drawable.bg_radius_d6_ffc4c4c4,
        errorRes: Int = R.drawable.bg_radius_d6_ffc4c4c4,
        res: Int = 0 //图片本地地址
    ) {
        var options = RequestOptions()
        //圆角
        if (radius > 0) {
            options = options.transform(CenterCrop(), RoundedCornersTransformation(radius.toInt(), 0,
                RoundedCornersTransformation.CornerType.ALL))
        }
        options = options.placeholder(defaultRes)
            .error(errorRes)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        //本地图片处理
        if (res != 0) {
            Glide.with(imageView.context).load(res).apply(options).into(imageView)
            return
        }
        Glide.with(imageView.context).load(uri).apply(options).into(imageView)
    }


    /**
     * 加载圆角(一半圆角)图片
     *
     * @param url
     * @param imageView
     * @param radius
     */
    @SuppressLint("CheckResult")
    fun loadImgHalfRadius(url: String,
                          imageView: ImageView, radius: Float, defaultRes: Int = R.drawable.bg_radius_d6_ffc4c4c4) {
        LogUtil.i("glide", "url:$url")
        val options = RequestOptions.bitmapTransform(MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(radius.toInt(), 0,
                RoundedCornersTransformation.CornerType.TOP)))
        options.placeholder(defaultRes)
            .error(defaultRes)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(imageView.context).load(url).apply(options).into(imageView)
    }


}