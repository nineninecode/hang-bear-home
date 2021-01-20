package com.wzh.lab.demo;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/15 16:57
 */
public class Mouse {
    public static void main(String[] args) throws AWTException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width = new Double(d.getWidth()).intValue();
        int height = new Double(d.getHeight()).intValue();
        Robot robot = new Robot();
        //robot.mouseMove(3717-1920, 50);
        //robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        //int maskForButton = MouseEvent.getMaskForButton(InputEvent.BUTTON1_MASK);
        //System.out.println(maskForButton);
        int numberOfButtons = MouseInfo.getNumberOfButtons();
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        System.out.println(pointerInfo.getLocation());
        System.out.println(numberOfButtons);
        //robot.mouseMove(width/2+100, height/2+100);
        //robot.mouseRelease(InputEvent.BUTTON1_MASK);
        //robot.mouseMove(1004,480);
        //robot.keyPress(56);
        // 获取屏幕分辨率
        //System.out.println(d);
    }
}
