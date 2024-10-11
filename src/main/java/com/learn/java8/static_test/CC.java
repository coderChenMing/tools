package com.learn.java8.static_test;

public class CC {
    // 静态代码块
    static {
        System.out.println("我是子类 C 的普通成员变量 CC 的静态代码块");
    }
    // 普通代码块
    {
        System.out.println("我是子类 C 的普通成员变量 CC 的普通代码块");
    }
    // 无参构造方法
    public CC() {
        System.out.println("我是子类 C 的普通成员变量 CC 的无参构造方法");
    }
}
