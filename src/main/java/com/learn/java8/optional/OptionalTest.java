package com.learn.java8.optional;

import java.util.Optional;

/**
 * orElse() 和 orElseGet()特别相似，有必要抽离出来讲下他们之间的区别。
 * <p>
 * orElse() 方法无论 Optional 对象是否为空都会执行，因此它总是会创建一个新的对象。orElseGet() 方法只有在 Optional 对象为空时才会执行，
 * 因此它可以用来延迟创建新的对象。
 */
public class OptionalTest {
    public static void main(String[] args) {
        System.out.println("--------不为null的情况----------");
        //不为 null
        String str1 = "hello";
        String result11 = Optional.ofNullable(str1).orElse(get(str1 + ":orElse()方法被执行了"));
        String result12 = Optional.ofNullable(str1).orElseGet(() -> get(str1 + ":orElseGet()方法被执行了"));
        System.out.println(result11);
        System.out.println(result12);
        System.out.println("--------为null的情况----------");
        //为 null
        String str2 = null;
        String result21 = Optional.ofNullable(str2).orElse(get(str1 + ":orElse()方法被执行了"));
        String result22 = Optional.ofNullable(str2).orElseGet(() -> get(str2 + ":orElseGet()方法被执行了"));
        System.out.println(result21);
        System.out.println(result22);
    }

    public static String get(String name) {
        System.out.println(name);
        return name;
    }
}
