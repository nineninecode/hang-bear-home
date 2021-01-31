package com.wzh.lab.lol.service;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.wzh.lab.lol.Params;
import com.wzh.lab.utils.WinRobotUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/30 11:09
 */
@Slf4j
public class LOLService {

    /**
     * 下棋，备战环节流程
     */
    public void playChess() {
        log.info("进入备战环节自动操作流程");
        // 经验自动+2
        Params.experience = Params.experience + 2;
        // 环节自动+1
        Params.stageNUm = Params.stageNUm + 1;
        calculateLevel();
        log.info("等级:{}，经验值：{}，环节数：{}", Params.level, Params.experience, Params.stageNUm);
        // TODO 卖从左至右第一个空位的棋子，卖出选秀结果

        List<String> freshPieces = Params.freshPieces;
        // 自动刷新购买
        buyPiece(freshPieces);
        // 固定升级
        if (Params.experience == 4 || Params.experience == 14) {
            buyExperience();
            calculateLevel();
            Params.money = Params.money - 4;
            log.info("固定升级，等级:{}，金币：{}，经验值：{}，", Params.level, Params.money, Params.experience);
        }
        // 7级之下50金币以上，购买经验
        while (Params.money > 53 && Params.level < 7) {
            buyExperience();
            calculateLevel();
            Params.money = Params.money - 4;
            log.info("固定升级，等级:{}，金币：{}，经验值：{}，", Params.level, Params.money, Params.experience);
        }

        // 7级，刷完所有钱
        if (Params.money > 10 && Params.level > 6) {
            freshPiece();
            recognizePieces();
            freshPieces = Params.freshPieces;
            log.info("刷新棋子:{}", Params.freshPieces);
            buyPiece(freshPieces);
        }

        // 自动上棋子

    }

    /**
     * 购买棋子
     * 
     * @param freshPieces
     *            给出的棋子
     */
    private void buyPiece(List<String> freshPieces) {
        for (int i = 0; i < freshPieces.size(); i++) {
            String s = freshPieces.get(i);
            if (Params.needPieces.contains(s)) {
                // 购买
                WinRobotUtils.leftMouseSinglePress(Params.piecePoints.get(i));
                log.info("购买棋子:{}", s);
                // 睡眠0.2秒。给与反应时间
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 增加经验，经验值+4
     */
    public void buyExperience() {
        WinRobotUtils.pressKey(KeyEvent.VK_F);
        Params.experience = Params.experience + 4;
    }

    /**
     * 刷新棋子
     */
    public void freshPiece() {
        WinRobotUtils.pressKey(KeyEvent.VK_D);
    }

    /**
     * 识别棋子
     */
    public void recognizePieces() {
        try {
            Params.executors.invokeAll(Params.pieceTasks, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 卖出板凳席上的棋子
     * 
     * @param index
     *            板凳号
     */
    private void saleBenchPiece(int index) {
        // 移动
        WinRobotUtils.moveMouse(Params.benchPiecePoints.get(index));
        // 卖出
        WinRobotUtils.pressKey(KeyEvent.VK_E);
        // 睡眠0.2秒。给与反应时间
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算等级
     */
    public void calculateLevel() {
        if (Params.experience > 0 && Params.experience < 3) {
            Params.level = 2;
        }
        // 3级6经验
        else if (Params.experience > 3 && Params.experience < 7) {
            Params.level = 3;
        }
        // 4级10经验
        else if (Params.experience > 7 && Params.experience < 15) {
            Params.level = 4;
        }
        // 5级20经验
        else if (Params.experience > 15 && Params.experience < 33) {
            Params.level = 5;
        }
        // 6级36经验
        else if (Params.experience > 33 && Params.experience < 67) {
            Params.level = 6;
        }
        // 7级50经验
        else if (Params.experience > 71 && Params.experience < 119) {
            Params.level = 7;
        }
        // 8级80经验
        else if (Params.experience > 119 && Params.experience < 177) {
            Params.level = 8;
        }
        // 9级
        else if (Params.experience > 177) {
            Params.level = 9;
        }

    }
}
