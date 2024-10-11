package com.learn.java8.static_test;

public class BB {
    // 静态代码块
    static {
        System.out.println("我是父类 B 的普通成员变量 BB 的静态代码块");
    }

    // 普通代码块
    {
        System.out.println("我是父类 B 的普通成员变量 BB 的普通代码块");
    }

    // 无参构造方法
    public BB() {
        System.out.println("我是父类 B 的普通成员变量 BB 的无参构造方法");
    }
}
