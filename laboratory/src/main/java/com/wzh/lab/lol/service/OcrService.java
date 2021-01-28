package com.wzh.lab.lol.service;

import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import com.wzh.lab.utils.OcrUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:14
 */
@Slf4j
public class OcrService {

    private final Lock lock = new ReentrantLock();
    /**
     * conditionA，使用多个Condition实现通知部分线程
     */
    public Condition condition = lock.newCondition();

    public OcrService() {}

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

    public String doOcr(BufferedImage imageFile) {
        await();
        // 等待结束，执行ocr
        String result = null;
        result = OcrUtils.doOCR(imageFile);
        log.info(result);
        return result;
    }

    public void signalAll() {
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
