package com.wzh.lab.lol;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

    public static Point lolIcon = new Point(780, 30);
    public static Point startIcon = new Point(1735, 1707);
    public static Point acceptIcon = new Point(1735, 1600);
    public static Rectangle acceptRectangle = new Rectangle(1735, 1600, 200, 200);
    public static Rectangle stageRectangle = new Rectangle(1735, 1600, 200, 200);
    public static Rectangle moneyRectangle = new Rectangle(1735, 1600, 200, 200);
    public static List<Rectangle> pieceRectangles = new ArrayList<>();
    public static List<Rectangle> bloodRectangles = new ArrayList<>();
    public static int money = 0;
    public static int level = 0;
    public static int blood = 0;

    static {
        // 起始位置x:960,y:1855，x每次增加403
        int imgNum = 5;

        for (int i = 0; i < imgNum; i++) {
            int x = 965 + 403 * i;
            Rectangle rectangle = new Rectangle(x, 2085, 280, 50);
            pieceRectangles.add(rectangle);
        }

        int personNum = 8;
        for (int i = 0; i < personNum; i++) {
            int y = 965 + 403 * i;
            Rectangle rectangle = new Rectangle(2085, y, 280, 50);
            pieceRectangles.add(rectangle);
        }
    }
}
