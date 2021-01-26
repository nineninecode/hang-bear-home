package com.wzh.lab.juc;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 多线程通信wait-demo
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 10:17
 */
public class ThreadWaitDemo {

    public static void main(String[] args) throws InterruptedException {

        // 锁对象
        String lock = new String("");

        ThreadSubtract subtract1Thread = new ThreadSubtract(lock, "subtract1Thread");
        subtract1Thread.start();

        ThreadSubtract subtract2Thread = new ThreadSubtract(lock, "subtract2Thread");
        subtract2Thread.start();

        Thread.sleep(1000);

        ThreadAdd addThread = new ThreadAdd(lock, "addThread");
        addThread.start();

    }

}

/**
 * 资源类
 */
class ValueObject {
    public static List<String> list = new ArrayList<String>();
}

/**
 * 元素添加线程
 */
class ThreadAdd extends Thread {

    private String lock;

    public ThreadAdd(String lock, String name) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            ValueObject.list.add("anyString");
            // 唤醒所有 wait 线程
            lock.notifyAll();
        }
    }
}

/**
 * 元素删除线程
 */
class ThreadSubtract extends Thread {

    private String lock;

    public ThreadSubtract(String lock, String name) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                // if (ValueObject.list.size() == 0) {
                // 使用if会得不到其他线程操作后ValueObject的变化，导致数组越界报错
                while (ValueObject.list.size() == 0) {
                    System.out.println("wait begin ThreadName=" + Thread.currentThread().getName());
                    // 唤醒这里开始执行
                    lock.wait();
                    System.out.println("wait   end ThreadName=" + Thread.currentThread().getName());
                }
                ValueObject.list.remove(0);
                System.out.println("list size=" + ValueObject.list.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
