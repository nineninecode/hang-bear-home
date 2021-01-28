package com.wzh.lab.lol.service;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private ITesseract instance;

    private final Lock lock = new ReentrantLock();
    /**
     * conditionA，使用多个Condition实现通知部分线程
     */
    public Condition condition = lock.newCondition();

    public OcrService() {
        instance = new Tesseract();
        String path = OcrService.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        path = path.substring(1);
        // 指定语言库目录
        instance.setDatapath(path);
        instance.setTessVariable("user_defined_dpi", "300");
        instance.setLanguage("chi_sim");
    }

    public void await() {
        lock.lock();
        try {
            System.out.println(
                "begin awaitA时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            condition.await();
            System.out.println(
                "  end awaitA时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String doOCR(BufferedImage imageFile) {
        await();
        // 等待结束，执行ocr
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        log.info(result);
        return result;
    }

    public void signalAll() {
        lock.lock();
        try {
            System.out.println(
                "signalAll时间为" + System.currentTimeMillis() + " ThreadName=" + Thread.currentThread().getName());
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
