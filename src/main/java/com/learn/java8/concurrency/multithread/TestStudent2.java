package com.learn.java8.concurrency.multithread;

import com.learn.java8.concurrency.xiaoming.Punishment;

public class TestStudent2 {
    public static void main(String[] args) {
       //testS2();
       testS2v2();
    }

    public static void testS2() {
        Punishment punishment = new Punishment(100,"internationalization");
        Student2 student = new Student2("小明",punishment);
        student.start();
        System.out.println("Another thread will finish the punishment。 main thread is finished" );
    }

    public static void testS2v2() {
        Punishment punishment = new Punishment(10000,"internationalization");
        Student2 xiaoming = new Student2("小明",punishment);
        xiaoming.start();

        Student2 xiaozhang = new Student2("小张",punishment);
        xiaozhang.start();

        Student2 xiaozhao = new Student2("小赵", punishment);
        xiaozhao.start();

        System.out.println("Another thread will finish the punishment。 main thread is finished" );
    }
}
