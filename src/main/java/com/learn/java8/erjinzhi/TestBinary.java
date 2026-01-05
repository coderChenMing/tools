package com.learn.java8.erjinzhi;

import java.math.BigInteger;

public class TestBinary {
    public static void main(String[] args) {
        int a = 53;
        String binaryStr = decimal2Binary(a);
        System.out.println(binaryStr);
        String binary = "110101";
        int decimal = binary2Decimal(binary);
        System.out.println(decimal);
    }

    public static String decimal2Binary(int decimal) {
        BigInteger bigInteger = new BigInteger(String.valueOf(decimal));
        return bigInteger.toString(2);
    }

    public static int binary2Decimal(String binary) {
        BigInteger bigInteger = new BigInteger(binary,2);
        //return bigInteger.intValue();
        return Integer.parseInt(bigInteger.toString());
    }
}
