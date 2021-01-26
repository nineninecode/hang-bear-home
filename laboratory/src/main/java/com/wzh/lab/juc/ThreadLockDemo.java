package com.wzh.lab.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 多线程通信wait-demo
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 10:17
 */
public class ThreadLockDemo {

    public static void main(String[] args) throws InterruptedException {

        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();

        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();

        Thread.sleep(3000);
        service.signalAllForA();

    }

}

/**
 * 线程 A
 */
class ThreadA extends Thread {
    private final MyService service;

    public ThreadA(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.awaitA();
    }
}

/**
 * 线程 B
 */
class ThreadB extends Thread {
    private final MyService service;

    public ThreadB(MyService service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.awaitB();
    }
}

class MyService {
    private final Lock lock = new ReentrantLock();
    /**
     * conditionA，使用多个Condition实现通知部分线程
     */
    public Condition conditionA = lock.newCondition();
    public Condition conditionB = lock.newCondition();

    public void awaitA() {
        lock.lock();
        try {
            System.out.println(
                "begin awaitA时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            conditionA.await();
            System.out.println(
                "  end awaitA时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void awaitB() {
        lock.lock();
        try {
            System.out.println(
                "begin awaitB时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            conditionB.await();
            System.out.println(
                "  end awaitB时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalAllForA() {
        lock.lock();
        try {
            System.out.println(
                "  signalAll_A时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void signalAllForB() {
        lock.lock();
        try {
            System.out.println(
                "  signalAll_B时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
