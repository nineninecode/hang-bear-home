package com.wzh.lab.lol;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;


import com.wzh.lab.utils.ImageUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/24 10:42
 */
@Slf4j
public class StartChess {
    public static void main(String[] args) {

        Rectangle stageRectangle = new Rectangle(500, 600, 200, 200);
        String content = ImageUtils.getContent(stageRectangle);
        log.info(content);
        // 1.点击开始
        // 2.启动所有监控线程
        // 3.游戏开始
        // 3.1判断是否为准备阶段，否则每两秒循环判断
        // 3.2准备阶段，计时60秒
        // 3.2.1唤醒5个ocr线程识别棋子，5个线程判断完毕，主线程判断是否购买
        // 3.2.2获取阶段，金币，判断是否升级，是够刷新
        // 3.2.3刷新，进入3.2.1
        // 3.2.4准备阶段结束，可休眠60秒中剩余时间
        // 4.结束进程，过10分钟后每一分钟判断一次是否结束，结束点击退出，重新开始

    }
}
