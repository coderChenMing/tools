package com.learn.java8.static_test;

public class BBB {
    // 静态代码块
    static {
        System.out.println("我是父类 B 的静态成员变量 BBB 的静态代码块");
    }

    // 普通代码块
    {
        System.out.println("我是父类 B 的静态成员变量 BBB 的普通代码块");
    }

    // 无参构造方法
    public BBB() {
        System.out.println("我是父类 B 的静态成员变量 BBB 的无参构造方法");
    }
}
