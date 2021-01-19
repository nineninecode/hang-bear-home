package com.wzh.lab.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 15:19
 */
public class WinScreenUtils {

    /**
     * 屏幕截图
     * 
     * @param startX
     *            开始横坐标
     * @param startY
     *            开始纵坐标
     * @param width
     *            宽度
     * @param height
     *            高度
     * @return 图片
     */
    public static BufferedImage getScreenShot(int startX, int startY, int width, int height) {
        BufferedImage bfImage = null;
        int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(startX, startY, width, height));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return bfImage;
    }
}
