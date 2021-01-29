package com.wzh.lab.lol;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.wzh.lab.lol.task.PieceTask;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/28 16:32
 */
public class Params {

    public static CountDownLatch pieceCount = new CountDownLatch(5);
    /**
     * 是否继续
     */
    public static Boolean isContinue = Boolean.TRUE;
    /**
     * 当前对局是否结束
     */
    public static Boolean isEnd = Boolean.FALSE;

    /**
     * 图标坐标
     */
    public static Point lolIcon = new Point(780, 30);
    /**
     * 启动坐标
     */
    public static Point startIcon = new Point(1750, 1700);
    /**
     * 接受坐标坐标
     */
    public static Point acceptIcon = new Point(1920, 1480);
    /**
     * 接受矩形
     */
    public static Rectangle acceptRectangle = new Rectangle(1850, 1460, 120, 60);
    /**
     * 环节矩形
     */
    public static Rectangle stageRectangle = new Rectangle(1780, 300, 290, 90);
    /**
     * 金币矩形
     */
    public static Rectangle moneyRectangle = new Rectangle(1740, 1760, 80, 50);
    /**
     * 棋子矩形列表
     */
    public static List<Rectangle> pieceRectangles = new ArrayList<>();
    /**
     * 血量矩形列表
     */
    public static List<Rectangle> bloodRectangles = new ArrayList<>();
    /**
     * 金币
     */
    public static int money = 0;
    /**
     * 等级
     */
    public static int level = 0;
    /**
     * 血量
     */
    public static int blood = 0;
    /**
     * 刷新棋子列表
     */
    public static List<String> freshPieces = new ArrayList<>();
    /**
     * h获取棋子任务列表
     */
    public static List<PieceTask> pieceTasks = new ArrayList<>();

    static {
        // 起始位置x:960,y:1855，x每次增加403
        int imgNum = 5;

        for (int i = 0; i < imgNum; i++) {
            int x = 965 + 403 * i;
            Rectangle rectangle = new Rectangle(x, 2085, 280, 50);
            pieceRectangles.add(rectangle);
            pieceTasks.add(new PieceTask(rectangle, i));
        }

        int personNum = 8;
        for (int i = 0; i < personNum; i++) {
            int y = 422 + 143 * i;
            Rectangle rectangle = new Rectangle(3568, y, 90, 64);
            pieceRectangles.add(rectangle);
        }
    }
}
