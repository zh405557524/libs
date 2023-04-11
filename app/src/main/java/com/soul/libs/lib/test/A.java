package com.soul.libs.lib.test;

/**
 * Description: TODO
 * Author: 祝明
 * CreateDate: 2020/8/28 10:15
 * UpdateUser:
 * UpdateDate: 2020/8/28 10:15
 * UpdateRemark:
 */
public class A {

    public static int id = 100;

    {
        System.out.println("A");
    }

    static {
        System.out.println("state A");
    }

    public A() {
        System.out.println("hello A");
        System.out.println("id:"+id);
    }
}
