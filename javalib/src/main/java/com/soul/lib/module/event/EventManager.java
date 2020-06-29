
package com.soul.lib.module.event;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseArray;

import java.util.LinkedList;
import java.util.List;


public class EventManager {


    private final static int MSG_CMD_ADD_EVENT = 1;

    private final static int MSG_CMD_DEL_EVENT = 2;

    private final static int MSG_CMD_SEND_EVENT = 3;


    private static EventManager sInstance = null;

    private HandlerThread mWorkThread = null;

    private Handler mWorkHandler = null;

    private SparseArray<List<IModule>> mModules = null;


    public static EventManager getInstance() {
        if (sInstance == null) {
            synchronized (EventManager.class) {
                if (sInstance == null) {
                    sInstance = new EventManager();
                }
            }
        }
        return sInstance;
    }

    private EventManager() {

        mModules = new SparseArray<>();

        mWorkThread = new HandlerThread("EventManagerThread");

        mWorkThread.start();

        mWorkHandler = new Handler(mWorkThread.getLooper()) {

            public void handleMessage(Message msg) {
                final int cmd_type = msg.arg1;
                final Event data = (Event) msg.obj;
                dealMsg(cmd_type, data);
            }
        };
    }

    /**
     * 注册事件
     *
     * @param eventId 事件id
     * @param mod
     */
    public void regEvent(int eventId, IModule mod) {
        final Message message = createMessage(eventId, null, mod);
        message.arg1 = MSG_CMD_ADD_EVENT;
        mWorkHandler.sendMessage(message);
    }

    /**
     * 销毁事件
     *
     * @param eventId 事件id
     * @param mod
     */
    public void unRegEvent(int eventId, IModule mod) {
        final Message message = createMessage(eventId, null, mod);
        message.arg1 = MSG_CMD_DEL_EVENT;
        mWorkHandler.sendMessage(message);
    }

    /**
     * 发送事件
     *
     * @param eventId 事件id
     * @param data    处理的事件
     * @param mod
     */
    public void sendEvent(int eventId, Object data, IModule mod) {
        final Message message = createMessage(eventId, data, mod);
        message.arg1 = MSG_CMD_SEND_EVENT;
        mWorkHandler.sendMessage(message);
    }

    private void dealMsg(int cmd, Event event) {
        final int eventId = event.mEventId;
        final Object data = event.mData;
        final IModule mod = event.mMod;
        switch (cmd) {
            case MSG_CMD_ADD_EVENT://注册事件
                if (mModules != null) {
                    List<IModule> lists = mModules.get(eventId);
                    if (lists == null) {
                        lists = new LinkedList<>();
                    }
                    if (!lists.contains(mod)) {
                        lists.add(mod);
                    }
                    mModules.put(eventId, lists);
                }
                break;
            case MSG_CMD_DEL_EVENT://销毁事件
                if (mModules != null) {
                    List<IModule> lists = mModules.get(eventId);
                    if (lists != null) {
                        lists.remove(mod);
                        if (lists.size() == 0) {
                            mModules.remove(eventId);
                        }
                    }
                }
                break;
            case MSG_CMD_SEND_EVENT://发送事件
                if (mModules != null) {
                    final List<IModule> iModules = mModules.get(eventId);
                    if (iModules != null) {
                        for (IModule iModule : iModules) {
                            iModule.onEvent(eventId, data);
                        }
                    }
                }
                break;
        }
    }


    /**
     * 创建一个message
     *
     * @param eventId 事件id
     * @param object
     * @return
     */
    private Message createMessage(int eventId, Object object, IModule iModule) {
        final Event event = new Event();
        event.mEventId = eventId;
        event.mData = object;
        event.mMod = iModule;
        final Message obtain = Message.obtain();
        obtain.arg1 = eventId;
        obtain.obj = event;
        return obtain;
    }

}