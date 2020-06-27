package com.soul.lib.module.chain;

/**
 * @描述：责任链模式测试
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/23 18:39
 */
public class Test {


    public static void main(String[] arg) {

        ChainManger chainManger = new ChainManger();
        chainManger.addTask(new Task1());
        chainManger.addTask(new Task2());
        chainManger.addTask(new Task3());
        chainManger.doAction("no", chainManger);
    }

}
