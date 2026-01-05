package com.learn.java8.concurrency.geektime.lesson4;

public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    // 转账
     void transfer(
            Account target, int amt) {
         synchronized(Account.class) {
             if (this.balance > amt) {
                 this.balance -= amt;
                 target.balance += amt;
             }
         }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(200);
        Account b = new Account(200);
        Account c = new Account(200);
        Thread aThread = new Thread(() -> {
            a.transfer(b, 100);
            System.out.println("aThead" + b.balance);
        });
        /*try {
            aThread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        aThread.start();
        Thread bThread = new Thread(() -> {
            b.transfer(c, 100);
            System.out.println("bThread" + b.balance);
        });
        bThread.start();
        aThread.join();
        bThread.join();
        System.out.println(b.balance);
    }
}
