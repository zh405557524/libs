package com.soul.libs.lib

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.soul.lib.utils.LogUtil
import com.soul.libs.test.ButtonTextFragment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.Executors

/**
 * Description: 音频复制
 * Author: zhuMing
 * CreateDate: 2025/2/19 星期三 13:52
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2025/2/19 星期三 13:52
 * UpdateRemark:
 */
class CopyAudioFragment : ButtonTextFragment(), View.OnClickListener {
    val TAG= "CopyAudioFragment"
    override fun getClassName(): String {
        return "音频复制"
    }

    override fun initEvent() {
        addTextName("音频复制", this)
    }

    override fun onClick(v: View?) {
        val tag = v!!.tag as String
        when (tag) {
            "音频复制" -> {
                saveVideoToGallery(
                    requireContext(), "/sdcard/demo.mp4"
                )
            }
        }
    }


    //保存视频
    fun saveVideoToGallery(context: Context, videoFilePath: String) {
        LogUtil.i(TAG,"saveVideoToGallery")
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            // 在这里执行子线程任务
            val videoFile = File(videoFilePath)
            if (!videoFile.exists()) {
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    // 创建 ContentValues 来存储视频元数据
                    val contentValues = ContentValues().apply {
                        put(MediaStore.Video.Media.DISPLAY_NAME, videoFile.name) // 文件名
                        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4") // MIME类型
                        put(
                            MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM
                        ) // 相册路径
                    }
                    // 插入视频到 MediaStore
                    val uri: Uri? =
                        context.contentResolver.insert(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues
                        )
                    uri?.let {
                        // 将视频文件写入 MediaStore
                        context.contentResolver.openOutputStream(it)?.use { outputStream ->
                            FileInputStream(videoFile).use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                        // 通知相册更新
                        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, it))
                        Log.i("SaveVideo", "Video saved to gallery successfully")
                    } ?: run {
                        Log.e("SaveVideo", "Failed to insert video into MediaStore")
                    }
                } else {
                    // Android 9 及以下版本 (API 28-)，使用 File 方式存储
                    val videosDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    if (!videosDir.exists()) videosDir.mkdirs() // 确保目录存在

                    val targetFile = File(videosDir, videoFile.name)
                    try {
                        FileInputStream(videoFile).use { inputStream ->
                            FileOutputStream(targetFile).use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                        // 发送广播通知相册更新
                        context.sendBroadcast(
                            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(targetFile))
                        )
                        Log.i("SaveVideo", "Video saved to gallery successfully (API 28-)")
                    } catch (e: Exception) {
                        Log.e("SaveVideo", "Failed to save video: ${e.message}")
                    }
                }

            }
        }
        executor.shutdown() // 关闭Executor

    }

}