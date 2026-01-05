package com.learn.java8.erjinzhi;

public class TestSuanFa {
    public static void main(String[] args) {
        int base = 1;
        int isStudent = base;// 0001
        int isTeacher = base << 1; // 0010
        int isProgrammer = base << 2;// 0100
        int isAdmin = base << 3;//1000
        int code = 5;//0101
        if ((code & isStudent) != 0) {
            System.out.println(code + " isStudent");
        }
        if ((code & isTeacher) != 0) {
            System.out.println(code + " isTeacher");
        }
        if ((code & isProgrammer) != 0) {
            System.out.println(code + "isProgrammer");
        }
        if ((code & isAdmin) != 0) {
            System.out.println(code + "isAdmin");
        }
    }
}
