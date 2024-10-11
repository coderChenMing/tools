package com.learn.java8.static_test;

public class CCC {
    // 静态代码块
    static {
        System.out.println("我是子类 C 的静态成员变量 CCC 的静态代码块");
    }

    // 普通代码块
    {
        System.out.println("我是子类 C 的静态成员变量 CCC 的普通代码块");
    }

    // 无参构造方法
    public CCC() {
        System.out.println("我是子类 C 的静态成员变量 CCC 的无参构造方法");
    }
}
