package com.learn.java8.scanner;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        // test1();
        test2();
        double d=3.4;
    }

    public static void test1() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                System.out.println(n);
            } else {
                System.out.println("输入类型错误");
            }
        }
    }

    public static void test2() throws IOException {
        /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text = reader.readLine();
        System.out.println(text);*/
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        String s = scanner.nextLine();
        System.out.println(s);
        scanner.close();
    }
}
