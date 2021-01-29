package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 线程间协作类，使用lock和condition实现
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:14
 */
@Slf4j
public class MyLock {

    private final Lock lock = new ReentrantLock();
    /**
     * conditionA，使用多个Condition实现通知部分线程
     */
    public Condition condition = lock.newCondition();

    public MyLock() {}

    public void await() {
        lock.lock();
        try {
            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalAll() {
        lock.lock();
        try {
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
