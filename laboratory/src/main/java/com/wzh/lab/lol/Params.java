package com.wzh.lab.lol;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wzh.lab.lol.service.LOLService;
import com.wzh.lab.lol.task.*;

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

    public static ThreadPoolExecutor executors =
        new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    /**
     * 是否继续
     */
    public static Boolean isContinue = Boolean.TRUE;

    /**
     * 当前对局
     */
    public static String endStr = "";
    public static String endFlag = "现在退出";
    public static Boolean isEnd = Boolean.FALSE;

    /**
     * 阶段
     */
    public static String stage = "";
    public static String prepareStage = "备战环节";
    public static Boolean isPrepare = Boolean.FALSE;
    public static int stageNUm = 1;

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
    public static int blood = 100;

    /**
     * 刷新棋子列表
     */
    public static volatile List<String> freshPieces = new ArrayList<>();
    /**
     * 刷新血量列表
     */
    public static List<String> freshBloods = new ArrayList<>();

    /**
     * 图标坐标
     */
    public static Point lolIcon = new Point(780, 30);
    /**
     * 启动坐标
     */
    public static Point startIcon = new Point(1750, 1700);
    /**
     * 接受坐标
     */
    public static Point acceptIcon = new Point(1920, 1480);
    /**
     * 退出坐标
     */
    public static Point quitIcon = new Point(1700, 1099);
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
     * 结束矩形
     */
    public static Rectangle endRectangle = new Rectangle(1560, 1075, 188, 55);
    /**
     * 棋子矩形列表
     */
    public static List<Rectangle> pieceRectangles = new ArrayList<>();
    /**
     * 血量矩形列表
     */
    public static List<Rectangle> bloodRectangles = new ArrayList<>();
    /**
     * 获取棋子任务列表
     */
    public static List<PieceTask> pieceTasks = new ArrayList<>();
    /**
     * 获取血量和金币任务列表
     */
    public static List<RectangleTask> bloodAndMoneyTasks = new ArrayList<>();
    /**
     * 获取阶段和结束任务列表
     */
    public static List<RectangleTask> stageAndEndTasks = new ArrayList<>();

    /**
     * 棋子坐标列表
     */
    public static List<Point> piecePoints = new ArrayList<>();

    /**
     * 备战区棋子坐标列表
     */
    public static List<Point> benchPiecePoints = new ArrayList<>();

    public static String[] ownPieces = new String[9];

    public static int experience = 0;

    public static List<String> needPieces = new ArrayList<>();
    public static LOLService lolService = new LOLService();

    /**
     * 初始化数据
     */
    static {
        // 起始位置x:960,y:1855，x每次增加403
        int imgNum = 5;

        for (int i = 0; i < imgNum; i++) {
            int x = 965 + 403 * i;
            Rectangle rectangle = new Rectangle(x, 2085, 280, 50);
            pieceRectangles.add(rectangle);
            pieceTasks.add(new PieceTask(rectangle, i));
            freshPieces.add(String.valueOf(i));
            int x2 = 1200 + 403 * i;
            piecePoints.add(new Point(x2, 2000));
        }

        int personNum = 8;
        for (int i = 0; i < personNum; i++) {
            int y = 422 + 143 * i;
            Rectangle rectangle = new Rectangle(3568, y, 90, 64);
            bloodRectangles.add(rectangle);
            bloodAndMoneyTasks.add(new BloodTask(rectangle, i));
            freshBloods.add(String.valueOf(i));
        }
        bloodAndMoneyTasks.add(new MoneyTask(moneyRectangle));

        stageAndEndTasks.add(new StageTask(stageRectangle));
        stageAndEndTasks.add(new EndTask(endRectangle));

        // 备战区，共9个位置
        int benchNum = 9;
        for (int i = 0; i < benchNum; i++) {
            int x = 870 + 225 * i;
            benchPiecePoints.add(new Point(x, 1550));
        }

        needPieces.add("希瓦娜");
        needPieces.add("瑟提");
        needPieces.add("努努和威朗普");
        needPieces.add("奥恩");
        needPieces.add("科加斯");
        needPieces.add("蔚");
        needPieces.add("茂凯");
        needPieces.add("塔姆");
    }
}
