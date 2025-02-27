package com.soul.lib.module.recorder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;


import com.soul.lib.Global;
import com.soul.lib.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.core.content.ContextCompat;

/**
 * Description: 录音实现类
 * Author: 祝明
 * CreateDate: 2021/4/9 9:50
 * UpdateUser:
 * UpdateDate: 2021/4/9 9:50
 * UpdateRemark:
 */
public class AudioRecorder {

    public static String TAG = AudioRecorder.class.getSimpleName();

    //音频输入-麦克风
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private final static int AUDIO_SAMPLE_RATE = 16000;
    //声道 单声道0
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    //编码
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;

    //录音对象
    private AudioRecord audioRecord;

    //录音状态
    private Status status = Status.STATUS_NO_READY;

    //文件名
    private String fileName;

    //录音文件
    private List<String> filesName = new ArrayList<>();

    //线程池
    private ExecutorService mExecutorService;

    //录音监听
    private RecordStreamListener listener;

    private final String recorderPath;

    public interface RecordStreamListener {
        void onRecording(byte[] audiodata, int i, int length);

        void finishRecord();
    }


    public AudioRecorder() {
        mExecutorService = Executors.newCachedThreadPool();
        recorderPath = Global.getExternalCacheDir() + "recorder/";
    }

    /**
     * 判断录音通道被占用
     *
     * @return true 未被占用，false 被占用
     */
    public boolean validateMicAvailability() {
        if (ContextCompat.checkSelfPermission(Global.getContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        Boolean available = true;
        @SuppressLint("MissingPermission") AudioRecord recorder =
                new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);
        try {
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                available = false;
            }

            recorder.startRecording();
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop();
                available = false;
            }
            recorder.stop();
        } finally {
            recorder.release();
        }
        return available;
    }

    /**
     * 创建录音对象
     *
     * @param fileName       文件名
     * @param audioSource    音频通道
     * @param sampleRateInHz 采样率
     * @param channelConfig  声道
     * @param audioFormat    编码
     */
    @SuppressLint("MissingPermission")
    public void createAudio(String fileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        if (ContextCompat.checkSelfPermission(Global.getContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, channelConfig);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        this.fileName = fileName;
    }

    /**
     * 创建默认的录音对象
     *
     * @param fileName 文件名
     */
    @SuppressLint("MissingPermission")
    public void createDefaultAudio(String fileName) {
        if (ContextCompat.checkSelfPermission(Global.getContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,
                AUDIO_CHANNEL, AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
        this.fileName = fileName;
        status = Status.STATUS_READY;
    }


    /**
     * 开始录音
     */
    public void startRecord() {

        if (status == Status.STATUS_NO_READY || audioRecord == null) {
            throw new IllegalStateException("录音尚未初始化,请检查是否禁止了录音权限~");
        }
        if (status == Status.STATUS_START) {
            throw new IllegalStateException("正在录音");
        }
        Log.d("AudioRecorder", "===startRecord===" + audioRecord.getState());
        audioRecord.startRecording();

        String currentFileName = fileName;
        if (status == Status.STATUS_PAUSE) {
            //假如是暂停录音 将文件名后面加个数字,防止重名文件内容被覆盖
            currentFileName += filesName.size();

        }
        filesName.add(currentFileName);

        final String finalFileName = currentFileName;
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;

        //使用线程池管理线程
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                writeDataTOFile(finalFileName);
            }
        });
    }

    /**
     * 暂停录音
     */
    public void pauseRecord() {
        Log.d("AudioRecorder", "===pauseRecord===");
        if (status != Status.STATUS_START) {
            throw new IllegalStateException("没有在录音");
        } else {
            audioRecord.stop();
            status = Status.STATUS_PAUSE;
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        Log.d("AudioRecorder", "===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            throw new IllegalStateException("录音尚未开始");
        } else {
            audioRecord.stop();
            status = Status.STATUS_STOP;
            release();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        Log.d("AudioRecorder", "===release===");
        //假如有暂停录音
        try {
            if (filesName.size() > 0) {
                List<String> filePaths = new ArrayList<>();
                for (String fileName : filesName) {
                    filePaths.add(getPcmFileAbsolutePath(fileName));
                }
                //清除
                filesName.clear();
                //将多个pcm文件转化为wav文件
                //                mergePCMFilesToWAVFile(filePaths);

            } else {
                //这里由于只要录音过filesName.size都会大于0,没录音时fileName为null
                //会报空指针 NullPointerException
                // 将单个pcm文件转化为wav文件
                //Log.d("AudioRecorder", "=====makePCMFileToWAVFile======");
                //                makePCMFileToWAVFile();
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }

        status = Status.STATUS_NO_READY;
    }


    /**
     * 取消录音
     */
    public void cancel() {
        filesName.clear();
        fileName = null;
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }

        status = Status.STATUS_NO_READY;
    }

    public interface AudioPlayCallBack {
        void onCompletion();
    }

    /**
     * 播放pcm数据
     *
     * @param recordingFile
     */
    public void play(final String recordingFile, final AudioPlayCallBack audioPlayCallBack) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                DataInputStream dis = null;
                try {
                    //从音频文件中读取声音
                    dis = new DataInputStream(new BufferedInputStream(new FileInputStream(recordingFile)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //最小缓存区
                int bufferSizeInBytes = AudioTrack.getMinBufferSize(AUDIO_SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AUDIO_ENCODING);
                //创建AudioTrack对象   依次传入 :流类型、采样率（与采集的要一致）、音频通道（采集是IN 播放时OUT）、量化位数、最小缓冲区、模式
                AudioTrack player = new AudioTrack(AudioManager.STREAM_MUSIC, AUDIO_SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AUDIO_ENCODING, bufferSizeInBytes, AudioTrack.MODE_STREAM);

                byte[] data = new byte[bufferSizeInBytes];
                player.play();//开始播放
                while (true) {
                    int i = 0;
                    try {
                        while (dis.available() > 0 && i < data.length) {
                            data[i] = dis.readByte();//录音时write Byte 那么读取时就该为readByte要相互对应
                            i++;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    player.write(data, 0, data.length);
                    LogUtil.i(TAG, "play data :" + data);
                    if (i != bufferSizeInBytes) //表示读取完了
                    {
                        player.stop();//停止播放
                        player.release();//释放资源
                        break;
                    }


                }
                if (audioPlayCallBack != null) {
                    audioPlayCallBack.onCompletion();
                }
            }
        });


    }

    /**
     * 将音频信息写入文件
     */
    private void writeDataTOFile(String currentFileName) {
        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] audioData = new byte[bufferSizeInBytes];

        FileOutputStream fos = null;
        int readSize = 0;
        try {
            File file = new File(getPcmFileAbsolutePath(currentFileName));
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (IllegalStateException e) {
            Log.e("AudioRecorder", e.getMessage());
            throw new IllegalStateException(e.getMessage());
        } catch (FileNotFoundException e) {
            Log.e("AudioRecorder", e.getMessage());

        }
        while (status == Status.STATUS_START) {
            readSize = audioRecord.read(audioData, 0, bufferSizeInBytes);
            if (AudioRecord.ERROR_INVALID_OPERATION != readSize && fos != null) {
                try {
                    fos.write(audioData);
                    if (listener != null) {
                        //用于拓展业务
                        listener.onRecording(audioData, 0, audioData.length);
                    }
                } catch (IOException e) {
                    Log.e("AudioRecorder", e.getMessage());
                }
            }
        }
        if (listener != null) {
            listener.finishRecord();
        }
        try {
            if (fos != null) {
                fos.close();// 关闭写入流
            }
        } catch (IOException e) {
            Log.e("AudioRecorder", e.getMessage());
        }
    }

    /**
     * 将pcm合并成wav
     *
     * @param filePaths
     */
    private void mergePCMFilesToWAVFile(final List<String> filePaths) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if (PcmToWav.mergePCMFilesToWAVFile(filePaths, getWavFileAbsolutePath(fileName))) {
                    //操作成功
                } else {
                    //操作失败
                    Log.e("AudioRecorder", "mergePCMFilesToWAVFile fail");
                    throw new IllegalStateException("mergePCMFilesToWAVFile fail");
                }
            }
        });
    }


    /**
     * 将单个pcm文件转化为wav文件
     */
    private void makePCMFileToWAVFile() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if (PcmToWav.makePCMFileToWAVFile(getPcmFileAbsolutePath(fileName), getWavFileAbsolutePath(fileName), true)) {
                    //操作成功
                } else {
                    //操作失败
                    Log.e("AudioRecorder", "makePCMFileToWAVFile fail");
                    throw new IllegalStateException("makePCMFileToWAVFile fail");
                }
            }
        });
    }


    /**
     * 录音对象的状态
     */
    public enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP
    }

    /**
     * 获取录音对象的状态
     *
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 获取本次录音文件的个数
     *
     * @return
     */
    public int getPcmFilesCount() {
        return filesName.size();
    }


    public RecordStreamListener getListener() {
        return listener;
    }

    public void setListener(RecordStreamListener listener) {
        this.listener = listener;
    }

    /**
     * 获取pcm 的绝对路径
     *
     * @param fileName
     * @return
     */
    public String getPcmFileAbsolutePath(String fileName) {
        String pcmPath = recorderPath + "/pcm/";
        File file = new File(pcmPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pcmPath + fileName;
    }

    /**
     * 获取 wav 的路径
     *
     * @param fileName
     * @return
     */
    private String getWavFileAbsolutePath(String fileName) {
        String wavPath = recorderPath + "/wav/";
        File file = new File(wavPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return wavPath + fileName;
    }
}
