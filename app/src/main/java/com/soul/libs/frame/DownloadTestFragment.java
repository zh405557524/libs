package com.soul.libs.frame;

import android.view.View;

import com.soul.frame.download.DownloadManager;
import com.soul.frame.download.DownloadProgressListener;
import com.soul.lib.base.Global;
import com.soul.lib.test.ButtonTextFragment;
import com.soul.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:网络下载测试类
 * Author: 祝明
 * CreateDate: 2019/10/22 19:45
 * UpdateUser:
 * UpdateDate: 2019/10/22 19:45
 * UpdateRemark:
 */
public class DownloadTestFragment extends ButtonTextFragment implements View.OnClickListener {

    private static final String url = "http://192.168.10.11/demo.mp4";
    private static final String downloadFile = Global.getInnerDataDir();
    private List<Integer> mTaskList;
    private String TAG = "DownloadTestFragment";

    @Override
    public String getClassName() {
        return "网络下载测试";
    }

    @Override
    protected void initEvent() {
        mTaskList = new ArrayList<>();
        addTextName("添加一个下载任务", this);
        addTextName("删除一个下载任务", this);
        addTextName("暂停一个下载任务", this);
        addTextName("继续一个下载任务", this);
        addTextName("暂停所有下载任务", this);
        addTextName("继续所有下载任务", this);
        addTextName("删除所有下载任务", this);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        switch (tag) {
            case "添加一个下载任务": {
                try {
                    int taskId = DownloadManager.getInstance().addDownload(Global.getContext(), url, "点亮新世界.mp4");
                    mTaskList.add(taskId);
                    DownloadManager.getInstance().addDownloadListener(taskId, mDownloadProgressListener);
                    LogUtils.i(TAG, "添加一个下载任务：" + taskId);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, "添加下载任务失败");
                }
            }
            break;
            case "删除一个下载任务": {
                if (mTaskList.size() > 0) {
                    Integer taskId = mTaskList.get(mTaskList.size() - 1);
                    DownloadManager.getInstance().removeDownloadListener(taskId);
                    LogUtils.i(TAG, "删除一个下载任务：" + taskId);
                }
            }
            break;
            case "暂停一个下载任务": {
                if (mTaskList.size() > 0) {
                    Integer taskId = mTaskList.get(mTaskList.size() - 1);
                    DownloadManager.getInstance().pauseDownload(taskId);
                    LogUtils.i(TAG, "暂停一个下载任务：" + taskId);
                }
            }
            break;
            case "继续一个下载任务":
                if (mTaskList.size() > 0) {
                    Integer taskId = mTaskList.get(mTaskList.size() - 1);
                    DownloadManager.getInstance().resumeDownload(taskId);
                    LogUtils.i(TAG, "继续一个下载任务" + taskId);
                }
                break;
            case "暂停所有下载任务":
                DownloadManager.getInstance().pauseAllDownload();
                LogUtils.i(TAG, "暂停所有下载任务");
                break;
            case "继续所有下载任务":
                DownloadManager.getInstance().resumeAllDownload();
                LogUtils.i(TAG, "继续所有下载任务");
                break;
            case "删除所有下载任务":
                DownloadManager.getInstance().deleteAllDownload();
                LogUtils.i(TAG, "删除所有下载任务");
                break;
        }
    }


    private DownloadProgressListener mDownloadProgressListener = new DownloadProgressListener() {

        @Override
        public void pending(int taskId) {
            LogUtils.i(TAG, "pending:" + taskId);
        }

        @Override
        public void progress(int taskId, int total, int progress) {
            LogUtils.i(TAG, "taskId:" + taskId + "--total:" + total + "---progress:" + progress);
        }

        @Override
        public void completed(int taskId) {
            LogUtils.i(TAG, "completed:" + taskId);
        }

        @Override
        public void error(int taskId, Throwable e) {
            LogUtils.i(TAG, "error:" + taskId + "---Throwable:" + e.getMessage());
        }
    };
}
