package com.learn.java8.concurrency.multithread;

import com.learn.java8.concurrency.xiaoming.Punishment;

public class Student2 extends Thread {
    private String name;
    private Punishment punishment;

    public Student2(String name, Punishment punishment) {
        super(name);
        this.name=name;
        this.punishment = punishment;
    }

    public void copyWord() {
        int count = 0;
        String threadName = Thread.currentThread().getName();

        while (true) {
            if (punishment.getLeftCopyCount() > 0) {
                int leftCopyCount = punishment.getLeftCopyCount();
                System.out.println(threadName+"线程-"+name + "抄写" + punishment.getWordToCopy() + "。还要抄写" + --leftCopyCount + "次");
                punishment.setLeftCopyCount(leftCopyCount);
                count++;
            } else {
                break;
            }
        }

        System.out.println(threadName+"线程-"+name + "一共抄写了" + count + "次！");
    }
    @Override
    public void run() {
        copyWord();
    }
}
