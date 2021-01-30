package com.wzh.lab.lol;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import com.wzh.lab.lol.task.AcceptCallable;
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
    public static void main(String[] args) {

        WinRobotUtils.leftMouseSinglePress(Params.lolIcon);

        try {
            while (Params.isContinue) {
                AcceptCallable acceptTask = new AcceptCallable();
                Future<Boolean> submit = Params.executors.submit(acceptTask);
                // acceptTask不执行完毕会阻塞
                submit.get();
                // 进入对局
                while (true) {
                    long end;
                    end = System.currentTimeMillis();
                    Params.executors.invokeAll(Params.stageAndEndTasks, 2, TimeUnit.SECONDS);
                    log.info("阶段识别耗时:{}毫秒", System.currentTimeMillis() - end);
                    if (Params.isPrepare) {
                        log.info("进入准备阶段");
                        end = System.currentTimeMillis() + 60 * 1000;
                        log.info("bloodAndMoneyTasks {}", Params.bloodAndMoneyTasks.size());
                        log.info("pieceTasks {}", Params.pieceTasks.size());
                        // 识别血量，金币
                        Params.executors.invokeAll(Params.bloodAndMoneyTasks, 2, TimeUnit.SECONDS);
                        // 识别棋子
                        // 阻塞等待，全部执行完毕返回，若超过两秒也返回
                        Params.executors.invokeAll(Params.pieceTasks, 2, TimeUnit.SECONDS);
                        log.info("血量、棋子识别耗时:{}毫秒", System.currentTimeMillis() + 60 * 1000 - end);
                        log.info("睡眠:{}毫秒", end - System.currentTimeMillis());
                        Thread.sleep(end - System.currentTimeMillis());
                    } else if (Params.isEnd) {
                        break;
                    } else {
                        log.info("睡眠:2000毫秒");
                        Thread.sleep(2000);
                    }
                    logInfo();
                }

                // Params.isContinue = Boolean.FALSE;
            }
            Params.executors.shutdown();
        } catch (Exception e) {
            log.error("错误:{}", e.getMessage(), e);
        }

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

    private static void logInfo() {
        log.info("等级:{}，血量:{}，金币:{}", Params.level, Params.blood, Params.money);
        log.info("棋子:{}", Params.freshPieces);
        log.info("血量:{}", Params.freshBloods);
    }
}
