package com.learn.java8.concurrency.inter;

import com.learn.java8.concurrency.xiaoming.Punishment;

public class TestStudent3 {
    public static void main(String[] args) {
        Punishment punishment = new Punishment(100,"internationalization");
        Student3 student = new Student3("小明",punishment);
        Thread thread = new Thread(student,"小明");
        thread.start();
    }
}
