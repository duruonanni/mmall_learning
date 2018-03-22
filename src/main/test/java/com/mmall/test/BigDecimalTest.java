package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public void test1() {
        // 直接使用小数进行计算会出现误差
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public void test2() {
        // 直接使用BigDecimal进行计算,依旧会出现误差
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    @Test
    public  void test3() {
        // BigDecimal传入参数使用String类型可以确保精度
        // 商业计算中,一定要使用这种方法,存入数据库时再转成double
        // 可以使用一个工具类来进行转换
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }

}
