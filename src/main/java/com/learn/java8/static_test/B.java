package com.learn.java8.static_test;

public class B extends A {
    //普通成员变量
    private BB bb = new BB();
    //静态成员变量
    private static BBB bbb = new BBB();
    // 静态代码块
    static {
        System.out.println("我是父类 B 的静态代码块");
    }
    // 普通代码块
    {
        System.out.println("我是父类 B 的普通代码块");
    }
    // 无参构造方法
    public B() {
        System.out.println("我是父类 B 的无参构造方法");
    }
}
