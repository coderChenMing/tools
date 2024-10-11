package com.learn.java8.static_test;

public class C extends B {
    //普通成员变量
    private CC cc = new CC();

    //静态成员变量
    private static CCC ccc = new CCC();

    // 静态代码块
    static {
        System.out.println("我是子类 C 的静态代码块");
    }

    // 普通代码块
    static {
        System.out.println("我是子类 C 的普通代码块");
    }

    // 无参构造方法
    public C() {
        System.out.println("我是子类 C 的无参构造方法");
    }
}
