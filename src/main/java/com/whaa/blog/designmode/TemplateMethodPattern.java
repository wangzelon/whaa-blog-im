package com.whaa.blog.designmode;

/**
 * created by wangzelong 2022-01-13 17:47
 */
public class TemplateMethodPattern {
    public static void main(String[] args) {
        OneCook oneCook = new OneCook();
        oneCook.cook();
    }
}

abstract class AbstractCook {

    abstract void doOne();

    abstract void doTwo();

    abstract void doThree();

    public void cook() {
        this.doOne();
        this.doTwo();
        this.doThree();
    }
}

class OneCook extends AbstractCook {

    @Override
    void doOne() {
        System.out.println("one one");
    }

    @Override
    void doTwo() {
        System.out.println("one two");
    }

    @Override
    void doThree() {
        System.out.println("one three");
    }
}

class TwoCook extends AbstractCook {

    @Override
    void doOne() {
        System.out.println("two one");
    }

    @Override
    void doTwo() {
        System.out.println("two two");
    }

    @Override
    void doThree() {
        System.out.println("two three");
    }
}
