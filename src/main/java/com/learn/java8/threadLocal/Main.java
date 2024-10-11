package com.learn.java8.threadLocal;

public class Main {
    public static void main(String[] args) {
        // 在主线程中设置 InheritableThreadLocal
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set("Hello from the main thread");

        // 创建新的线程
        Thread thread = new Thread(() -> {
            // 子线程尝试获取 InheritableThreadLocal 的值
            String value = inheritableThreadLocal.get();
            System.out.println("In child thread, value from InheritableThreadLocal: " + value);
        });

        thread.start();  // 启动子线程

        try {
            thread.join();  // 等待子线程结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("In main thread, value from InheritableThreadLocal: " + inheritableThreadLocal.get());
    }
}
