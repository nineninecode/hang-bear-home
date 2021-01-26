package com.wzh.lab.lol.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础判断线程
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 22:12
 */
@Slf4j
public abstract class JudgeBaseThread implements Runnable {

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
     * 对比图片指纹
     */
    private String imageSign;

    public JudgeBaseThread(int startX, int startY, int imgWidth, int imgHeight, String imageSign) {
        this.startX = startX;
        this.startY = startY;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.imageSign = imageSign;
    }

    @Override
    public void run() {
        execute();
    }

    /**
     * 实际线程执行方法
     */
    public abstract void execute();

}
