package com.soul.kotlin.basics

/**
 * Description: kotlin语法
 * Author: zhuMing
 * CreateDate: 2020/6/28 18:15
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/6/28 18:15
 * UpdateRemark:
 */
class Grammar {


    fun main(args: Array<String>?) {
        //1、变量
        run {
            var a = 1
            val b: Int //不赋值需要定义类型
            b = 1
            a = 10
            val c = 3//val 不可改变
            log(a)
            log(b)
            log(c)
        }


        //2、函数
        run {
            val sum = sum(1, 2)
            log(sum)
        }

        //3、字符串模板
        run {
            var a = 1
            val s1 = "a is $a"
            print(s1)
            a = 2
            val s2 = "${s1.replace("is", "was")},but now is &a"
            print(s2)
        }

    }


    //函数，Int 返回值
    fun sum(a: Int, b: Int): Int {
        return a + b
    }


    //无返回值函数
    fun log(a: Int, c: Int) {
        print(a.toString() + c.toString())
    }

    fun log(a: Int) {
        print(a)
    }


}