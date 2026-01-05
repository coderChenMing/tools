package com.learn.java8.concurrency.xiaoming;

public class StudentClient {
    public static void main(String[] args) {
        Punishment punishment = new Punishment(100,"internationalization");
        Student student = new Student("小明",punishment);
        student.copyWord();
    }
}
