package com.whaa.blog.designmode;

/**
 * created by wangzelong 2022-01-14 11:03
 */
public class BuilderPattern {
}

class Product {
    public void doSomething() {

    }
}

abstract class Builder {
    abstract void setPart();

    abstract Product buildProduct();
}

class ConcreteProduct extends Builder {

    private Product product = new Product();

    @Override
    void setPart() {

    }

    @Override
    Product buildProduct() {
        return product;
    }
}

class Director {
    private Builder builder = new ConcreteProduct();

    public Product getProduct() {
        builder.setPart();
        return builder.buildProduct();
    }
}
