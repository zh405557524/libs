package com.soul.lib.module.chain;

/**
 * @描述：TODO
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/23 22:36
 */
public class Task1 extends IBaseTask {
    @Override
    public void doAction(String string, IBaseTask iBaseTask) {
        if ("no".equals(string)) {
            System.out.println("doAction: task1");
        } else {
            iBaseTask.doAction(string, iBaseTask);
        }
    }
}
