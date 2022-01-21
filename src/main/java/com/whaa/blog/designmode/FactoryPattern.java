package com.whaa.blog.designmode;

/**
 * created by wangzelong 2022-01-13 17:19
 */
public class FactoryPattern {
    public static void main(String[] args) {
        AbstractCarFactory carFactory = new CarFactory();
        System.out.println("-- 第一辆车生产特斯拉 --");
        Car tesla = carFactory.createCar(Tesla.class);
        tesla.name();
        tesla.drive();

        System.out.println("-- 第二辆车生产奔驰 --");
        Car benz = carFactory.createCar(Benz.class);
        benz.name();
        benz.drive();

        System.out.println("-- 第三辆车生产奥迪 --");
        Car audi = carFactory.createCar(Audi.class);
        audi.name();
        audi.drive();
    }
}

interface Car {

    void name();

    void drive();
}

class Tesla implements Car {

    @Override
    public void name() {
        System.out.println("我是特斯拉");
    }

    @Override
    public void drive() {
        System.out.println("我跑得快!!!");
    }
}

class Benz implements Car {

    @Override
    public void name() {
        System.out.println("我是奔驰");
    }

    @Override
    public void drive() {
        System.out.println("我是豪华车！！");
    }
}

class Audi implements Car {

    @Override
    public void name() {
        System.out.println("我是奥迪");
    }

    @Override
    public void drive() {
        System.out.println("我有四个圈");
    }
}

abstract class AbstractCarFactory {
    public abstract <T extends Car> T createCar(Class<T> clazz);
}

class CarFactory extends AbstractCarFactory {

    @Override
    public <T extends Car> T createCar(Class<T> clazz) {
        Car car = null;
        try {
            car = (Car)Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成汽车出错了");
        }
        return (T)car;
    }
}
