package com.wzh.lab.juc;

import net.sourceforge.tess4j.Tesseract;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 15:20
 */
public class MyTesseract extends Tesseract {

    private static MyTesseract myTesseract;

    private MyTesseract() {}

    public static MyTesseract getInstance() {
        if (null == myTesseract) {

            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                if (null == myTesseract) {
                    myTesseract = new MyTesseract();
                    String path = MyTesseract.class.getClassLoader().getResource("").getPath();
                    path = path + "/tessdata";
                    path = path.substring(1);
                    // 指定语言库目录
                    myTesseract.setDatapath(path);
                    myTesseract.setTessVariable("user_defined_dpi", "300");
                    myTesseract.setLanguage("chi_sim");
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
        return myTesseract;
    }
}
