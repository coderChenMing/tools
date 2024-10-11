package com.learn.java8.static_test;

public class AAA {
    // 静态代码块
    static {
        System.out.println("我是超级父类 A 的静态成员变量 AAA 的静态代码块");
    }

    // 普通代码块
    {
        System.out.println("我是超级父类 A 的静态成员变量 AAA 的普通代码块");
    }

    // 无参构造方法
    public AAA() {
        System.out.println("我是超级父类 A 的静态成员变量 AAA 的无参构造方法");
    }
}
