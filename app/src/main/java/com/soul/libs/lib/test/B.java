package com.soul.libs.lib.test;

/**
 * Description: TODO
 * Author: 祝明
 * CreateDate: 2020/8/28 10:16
 * UpdateUser:
 * UpdateDate: 2020/8/28 10:16
 * UpdateRemark:
 */
public class B extends A {
    public static int id = 101;


    static {
        System.out.println("state B");
    }

    {
        System.out.println("B");
    }

    public B() {
        System.out.println("hello B");
        System.out.println("id:" + id);
    }
}
