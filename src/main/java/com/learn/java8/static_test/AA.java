package com.learn.java8.static_test;

public class AA {
    // 静态代码块
    static {
        System.out.println("我是超级父类 A 的普通成员变量 AA 的静态代码块");
    }
    // 普通代码块
    {
        System.out.println("我是超级父类 A 的普通成员变量 AA 的普通代码块");
    }
    // 无参构造方法
    public AA() {
        System.out.println("我是超级父类 A 的普通成员变量 AA 的无参构造方法");
    }

}
