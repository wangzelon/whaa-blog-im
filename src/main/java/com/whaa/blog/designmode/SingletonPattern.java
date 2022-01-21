package com.whaa.blog.designmode;

/**
 * created by wangzelong 2022-01-13 17:06
 */
public class SingletonPattern {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Integer name = instance.getName();
        System.out.println(name);
        Singleton2 instance1 = Singleton2.getInstance();
        System.out.println(instance1.getName());
    }
}

class Singleton {
    private static Singleton INSTANCE = null;

    private Integer name = 1;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    public Integer getName() {
        return name;
    }
}

/**
 * 双重检查锁
 */
class Singleton2 {

    private static Singleton2 INSTANCE = null;
    private Integer name = 2;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton2();
                }
            }
        }
        return INSTANCE;
    }

    public Integer getName() {
        return name;
    }
}
