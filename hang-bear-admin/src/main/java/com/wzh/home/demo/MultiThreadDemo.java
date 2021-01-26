package com.wzh.home.demo;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 9:38
 */
public class MultiThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        String lock = "lock";
        ThreadA threadA = new ThreadA(lock);
        ThreadB threadB = new ThreadB(lock);
        threadA.start();
        threadB.start();
    }
}
