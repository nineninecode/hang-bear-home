package com.wzh.lab.lol.thread;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.wzh.lab.utils.WinScreenUtils;

/**
 * <p>
 * 截图线程，每秒截图一次
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 22:12
 */
@Slf4j
public class ScreenThread implements Runnable {

    /**
     * 截图开始坐标，X
     */
    private int startX;

    /**
     * 截图开始坐标，Y
     */
    private int startY;
    /**
     * 图片宽度
     */
    private int imgWidth;

    /**
     * 图片高度
     */
    private int imgHeight;

    /**
     * 截图保存文件夹
     */
    private String resourcePath;

    public ScreenThread(int startX, int startY, int imgWidth, int imgHeight, String resourcePath) {
        this.startX = startX;
        this.startY = startY;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.resourcePath = resourcePath;
    }

    @Override
    public void run() {

        while (true) {
            long id = IdUtil.getSnowflake(1, 1).nextId();
            String path = resourcePath + id + ".png";
            BufferedImage screenShot = WinScreenUtils.getScreenShot(startX, startY, imgWidth, imgHeight);
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
                ImageIO.write(screenShot, "PNG", out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 暂停一秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String resourcePath = "D:/lab/img/stage/";
        ScreenThread keyHook = new ScreenThread(1470, 250, 910, 220, resourcePath);
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(keyHook);
        log.info("screen piece thread started!");
    }
}
