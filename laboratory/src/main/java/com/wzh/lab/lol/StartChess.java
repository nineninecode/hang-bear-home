package com.wzh.lab.lol;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wzh.lab.lol.task.AcceptCallable;
import com.wzh.lab.utils.ImageUtils;
import com.wzh.lab.utils.WinRobotUtils;

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
    public static void main(String[] args) throws Exception {

        WinRobotUtils.leftMouseSinglePress(Params.lolIcon);
        ThreadPoolExecutor executors =
            new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        while (Params.isContinue) {
            AcceptCallable acceptTask = new AcceptCallable();
            Future<Boolean> submit = executors.submit(acceptTask);
            // acceptTask不执行完毕会阻塞
            submit.get();

            while (true) {
                String content = ImageUtils.getContent(Params.stageRectangle);
                log.info(content);
                boolean isPrepare = content.contains("备战环节");
                long end;
                if (isPrepare) {
                    end = System.currentTimeMillis() + 60 * 1000;
                    // 识别棋子
                    // 阻塞等待，全部执行完毕返回，若超过两秒也返回
                    executors.invokeAll(Params.pieceTasks, 2, TimeUnit.SECONDS);
                    Thread.sleep(end - System.currentTimeMillis());
                }
            }

            // Params.isContinue = Boolean.FALSE;
        }
        executors.shutdown();
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
