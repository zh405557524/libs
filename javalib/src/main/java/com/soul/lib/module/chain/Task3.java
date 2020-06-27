package com.soul.lib.module.chain;

import android.util.Log;

/**
 * @描述：TODO
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/23 22:36
 */
public class Task3 extends IBaseTask {
    @Override
    public void doAction(String string, IBaseTask iBaseTask) {
        if ("ok".equals(string)) {
            System.out.println("doAction: task3");
        } else {
            iBaseTask.doAction(string, iBaseTask);
        }
    }
}
