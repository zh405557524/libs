package com.soul.libs.lib;

/**
 * Description: TODO
 * Author: 祝明
 * CreateDate: 2020/8/28 10:16
 * UpdateUser:
 * UpdateDate: 2020/8/28 10:16
 * UpdateRemark:
 */
public class B extends A {
    static {
        System.out.println("state B");
    }
    {
        System.out.println("B");
    }

    public B() {
        System.out.println("hello B");
    }
}
