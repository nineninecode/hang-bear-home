package com.wzh.lab.juc.thread;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.wzh.lab.demo.OcrDemo;
import com.wzh.lab.utils.WinScreenUtils;

/**
 * <p>
 * 判断战斗进程线程
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 22:12
 */
@Slf4j
public class OcrThread implements Runnable {

    /**
     * OCR工作对象
     */
    private ITesseract instance;

    public OcrThread() {
        instance = new Tesseract();
        String path = OcrDemo.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        path = path.substring(1);
        // 指定语言库目录
        instance.setDatapath(path);
        instance.setTessVariable("user_defined_dpi", "300");
        instance.setLanguage("chi_sim");
    }

    @Override
    public void run() {

        while (true) {

            // 阶段提示截图
            BufferedImage screenImg = WinScreenUtils.getScreenShot(1470, 250, 910, 220);
            String result = "";
            // OCR
            try {
                result = instance.doOCR(screenImg);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
            // 备战环节截图
            if (result.contains("备战环节")) {
                screenPieces();
                // 暂停10秒
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {

                // 暂停一秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void screenPieces() {
        ScreenPieceData screenPieceData = new ScreenPieceData();
        List<Point> imagePoints = screenPieceData.getImagePoints();
        int imgWidth = screenPieceData.getImgWidth();
        int imgHeight = screenPieceData.getImgHeight();

        for (Point point : imagePoints) {
            long id = IdUtil.getSnowflake(1, 1).nextId();
            String resourcePath = "D:/lab/img/";
            String path = resourcePath + id + ".png";
            BufferedImage screenImg = WinScreenUtils.getScreenShot(point.x, point.y, imgWidth, imgHeight);
            BufferedOutputStream out = null;
            try {
                File file = new File(resourcePath);
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                    if (!mkdirs) {
                        throw new IOException("文件夹创建失败！");
                    }
                }
                out = new BufferedOutputStream(new FileOutputStream(path));
                ImageIO.write(screenImg, "PNG", out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String resourcePath = "D:/lab/img/9/";
        OcrThread keyHook = new OcrThread();
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(keyHook);
        log.info("screen piece thread started!");
    }
}
