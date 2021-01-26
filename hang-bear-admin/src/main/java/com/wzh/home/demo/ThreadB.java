package com.wzh.home.demo;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 9:33
 */
public class ThreadB extends Thread {
    private String lock;

    public ThreadB(String lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        // 当前线程必须获得锁才可以进行下面的操作
        synchronized (lock) {

            System.out.println("ThreadB 线程" + Thread.currentThread().getName() + "获取到了锁...");
            try {
                System.out.println("ThreadB 线程" + Thread.currentThread().getName() + "阻塞并释放锁...");
                lock.wait();
            } catch (InterruptedException e) {
            }
            System.out.println("ThreadB 线程" + Thread.currentThread().getName() + "执行完成...");


        }
    }
}
