package com.learn.java8.static_test;


/**
 * @title A
 * @describe 超级父类 A
 */
public class A {

    //普通成员变量
    private AA aa = new AA();

    //静态成员变量
    private static AAA aaa = new AAA();

    // 静态代码块
    static {
        System.out.println("我是超级父类 A 的静态代码块");
    }

    // 普通代码块
    {
        System.out.println("我是超级父类 A 的普通代码块");
    }

    // 无参构造方法
    public A() {
        System.out.println("我是超级父类 A 的无参构造方法");
    }
}
