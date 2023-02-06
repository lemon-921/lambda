package com.dreamcatcher;

import java.util.function.IntBinaryOperator;

/**
 * @author zgl
 * @create 2023-02-2023/2/4-17:15
 */
public class LambdaDemo01 {

    public static void main(String[] args) {
        //使用lambda简化要求  如果匿名内部类是个接口，并且这个接口只有一个抽象方法重写
        new Thread(new Runnable() { //使用匿名内部类
            @Override
            public void run() {
                System.out.println("线程中的run方法被执行了");
            }
        }).start();

        //使用lambda
        new Thread(() -> {
            System.out.println("使用lambda方式，线程中的run方法被执行了");
        }).start();


        int num = calculateNum(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        });
        System.out.println(num);

        int num1 =calculateNum((int left, int right) -> {
            return left + right;
        });

        System.out.println(num1);
    }

    public static int calculateNum(IntBinaryOperator operator) {//二进制运算符
        int a = 10;
        int b = 20;
        return operator.applyAsInt(a, b);

    }

}
