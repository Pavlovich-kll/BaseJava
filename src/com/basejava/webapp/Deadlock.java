package com.basejava.webapp;

public class Deadlock {

    public static void main(String[] args) {
        Object x = new Object();
        Object y = new Object();

        doThread(x, y, ": got x monitor", ": trying to get y monitor");
        doThread(y, x, ": got y monitor", ": trying to get x monitor");
    }

    private static void doThread(Object x, Object y, String s, String s2) {
        new Thread(() -> {
            synchronized (x) {
                System.out.println(Thread.currentThread().getName() + s);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + s2);
                synchronized (y) {
                    System.out.println(Thread.currentThread().getName() + ": got y monitor");
                }
            }
        }).start();
    }
}