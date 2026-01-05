package com.learn.java8.concurrency.geektime.lesson3;

public class SafeCalc {
    long value = 0L;

    long get() {
        return value;
    }

    synchronized void addOne() {
        value += 1;
    }

    public static void main(String[] args) {
        SafeCalc safeCalc = new SafeCalc();
        Runnable run1 = () -> {
            for (int i = 0; i < 100000; i++) {
                safeCalc.addOne();
                System.out.println("run1====" + safeCalc.get());
            }
        };
        Runnable run2 = () -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println("run2====" + safeCalc.get());
            }
        };
        new Thread(run1).start();
        new Thread(run2).start();
    }
}
