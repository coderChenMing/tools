package com.learn.java8.erjinzhi;

public class TestWei {
    public static void main(String[] args) {
        int num = 53;
        int m = 1;
        System.out.println(String.format("数字%d的二进制向左移%d位是%d", num, m, TestWei.leftShift(num, m)));   //测试向左移位
        System.out.println(String.format("数字%d的二进制向右移%d位是%d", num, m, TestWei.rightShift(num, m)));   //测试向右移位

        System.out.println();

        m = 3;
        System.out.println(String.format("数字%d的二进制向左移%d位是%d", num, m, TestWei.leftShift(num, m)));   //测试向左移位
        System.out.println(String.format("数字%d的二进制向右移%d位是%d", num, m, TestWei.rightShift(num, m)));   //测试向右移位

        int a = 53;
        int b = 35;
        System.out.println();

        System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘或’结果是%d(%s)",
                a, TestBinary.decimal2Binary(a), b, TestBinary.decimal2Binary(b), TestWei.or(a, b), TestBinary.decimal2Binary(TestWei.or(a, b)))); //获取十进制数53和35的按位“或”

        System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘与’结果是%d(%s)",
                a, TestBinary.decimal2Binary(a), b, TestBinary.decimal2Binary(b), TestWei.and(a, b), TestBinary.decimal2Binary(TestWei.and(a, b))));  //获取十进制数53和35的按位“与”

        System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘异或’结果是%d(%s)",
                a, TestBinary.decimal2Binary(a), a, TestBinary.decimal2Binary(a), TestWei.xor(a, a), TestBinary.decimal2Binary(TestWei.xor(a, a))));  //获取十进制数53和35的按位“异或”
    }

    public static int leftShift(int num, int n) {
        return num << n;
    }
    // Java 里定义了两种右移，逻辑右移和算术右移。逻辑右移 1 位，左边补 0 即可。
    // 算术右移时保持符号位不变，除符号位之外的右移一位并补符号位 1。补的 1 仍然在符号位之后。
    // 逻辑右移在 Java 和 Python 语言中使用 >>> 表示，而算术右移使用 >> 表示
    public static int rightShift(int num, int n) {
        return num >> n;
        //return num >>> n;
    }

    /**
     * @Description: 二进制按位“或”的操作
     * @param num1-第一个数字，num2-第二个数字
     * @return 二进制按位“或”的结果
     */
    public static int or(int num1, int num2) {

        return (num1 | num2);

    }

    /**
     * @Description: 二进制按位“与”的操作
     * @param num1-第一个数字，num2-第二个数字
     * @return 二进制按位“与”的结果
     */
    public static int and(int num1, int num2) {

        return (num1 & num2);

    }

    /**

     * @Description: 二进制按位“异或”的操作
     * @param num1-第一个数字，num2-第二个数字
     * @return 二进制按位“异或”的结果
     */

    public static int xor(int num1, int num2) {

        return (num1 ^ num2);

    }
}
