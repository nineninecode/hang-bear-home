package com.wzh.lab.juc.thread;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

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


import com.wzh.lab.utils.ImageUtils;
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
public class JudgeThread implements Runnable {

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

    public JudgeThread(int startX, int startY, int imgWidth, int imgHeight, String resourcePath) {
        this.startX = startX;
        this.startY = startY;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.resourcePath = resourcePath;
    }

    @Override
    public void run() {

        while (true) {
            BufferedImage screenShot = WinScreenUtils.getScreenShot(startX, startY, imgWidth, imgHeight);
            String imageSign = ImageUtils.getImageSign(screenShot);

            String prepareSign = "";
            // 准备阶段先截一次图
            if (ImageUtils.isSameImage(imageSign, prepareSign, 6)) {
                ScreenPieceData screenPieceData = new ScreenPieceData();
                List<Point> imagePoints = screenPieceData.getImagePoints();
                int imgWidth = screenPieceData.getImgWidth();
                int imgHeight = screenPieceData.getImgHeight();

                for (Point point : imagePoints) {
                    long id = IdUtil.getSnowflake(1, 1).nextId();
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

            // 暂停一秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String resourcePath = "D:/lab/img/9/";
        JudgeThread keyHook = new JudgeThread(40, 54, 200, 200, resourcePath);
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(keyHook);
        log.info("screen piece thread started!");
    }
}
