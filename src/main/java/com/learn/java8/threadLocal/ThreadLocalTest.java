package com.learn.java8.threadLocal;

public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            threadLocal.set("本地变量1");
            print("thread1");
            System.out.println("线程1的本地变量的值为:" + threadLocal.get());
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set("本地变量2");
            print("thread2");
            System.out.println("线程2的本地变量的值为:" + threadLocal.get());
        });

        thread1.start();
        thread2.start();
    }

    public static void print(String s) {
        System.out.println(s + ":" + threadLocal.get());

    }
}
