package com.wzh.lab.utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.math.BigDecimal;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/28 23:14
 */
public class WinRobotUtils {

    private static Robot robot;

    private static BigDecimal ratio;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        // 桌面缩放比例，基准为96
        BigDecimal baseScreen = new BigDecimal(96);
        BigDecimal screenResolution = new BigDecimal(Toolkit.getDefaultToolkit().getScreenResolution());
        ratio = screenResolution.divide(baseScreen, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 鼠标左键单击某个位置
     * 
     * @param point
     *            位置坐标
     */
    public static void leftMouseSinglePress(Point point) {
        int x = new BigDecimal(point.x).divide(ratio, 2, BigDecimal.ROUND_HALF_UP).intValue();
        int y = new BigDecimal(point.y).divide(ratio, 2, BigDecimal.ROUND_HALF_UP).intValue();
        robot.mouseMove(-1, -1);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /**
     * 按下某个键
     * 
     * @param keyInt
     *            键位int
     */
    public static void pressKey(int keyInt) {

        robot.keyPress(keyInt);
        robot.delay(200);
        robot.keyRelease(keyInt);
    }
}
