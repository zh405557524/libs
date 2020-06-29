package com.soul.lib.module.chain;

/**
 * @描述：责任链模式
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/23 17:50
 */
public abstract class IBaseTask {
    public static final String TAG = "Tag";

    public abstract void doAction(String string, IBaseTask iBaseTask);
}



