package com.soul.lib.module.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述：责任链模式
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/23 18:37
 */
public class ChainManger extends IBaseTask {

    private int index;

    private List<IBaseTask> mIBaseTasks = new ArrayList<>();

    public void addTask(IBaseTask iBaseTask) {
        mIBaseTasks.add(iBaseTask);
    }

    public void doAction(String string, IBaseTask iBaseTask) {

        if (index >= mIBaseTasks.size()) {
            return;
        }

        IBaseTask iBaseTask1 = mIBaseTasks.get(index);
        index++;
        iBaseTask1.doAction(string, iBaseTask);

    }


}
