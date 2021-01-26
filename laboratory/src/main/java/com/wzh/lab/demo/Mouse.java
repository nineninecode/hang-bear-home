package com.wzh.lab.demo;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * <p>
 * idea 以管理员权限运行，可以在类似LOL中生效点击事件
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/15 16:57
 */
public class Mouse {
    public static void main(String[] args) throws AWTException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
        System.out.println(screenResolution);
        int width = new Double(d.getWidth()).intValue();
        int height = new Double(d.getHeight()).intValue();
        System.out.println("x:" + width + "  y:" + height);
        Robot robot = new Robot();
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        System.out.println(pointerInfo.getLocation());
        //robot.mouseMove(0, 0);
        //pointerInfo = MouseInfo.getPointerInfo();
        //System.out.println(pointerInfo.getLocation());
        //robot.delay(2000);
        //robot.mouseMove(3840, 2160);
        //pointerInfo = MouseInfo.getPointerInfo();
        //System.out.println(pointerInfo.getLocation());
        //robot.mouseMove(0, 0);
        //pointerInfo = MouseInfo.getPointerInfo();
        //System.out.println(pointerInfo.getLocation());
        robot.mouseMove(-1, -1);
        robot.delay(2000);
        robot.mouseMove((int)(780 / 1.5), (int)(30 / 1.5));
        pointerInfo = MouseInfo.getPointerInfo();
        System.out.println(pointerInfo.getLocation());
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(-1, -1);
        robot.mouseMove((int)(1735 / 1.5), (int)(1707 / 1.5));
        pointerInfo = MouseInfo.getPointerInfo();
        System.out.println(pointerInfo.getLocation());
        robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(500);
        robot.mouseMove(-1, -1);
        robot.mouseMove((int)(1550 / 1.5), (int)(1720 / 1.5));
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.keyPress(KeyEvent.VK_D);
        System.out.println(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);

        // int maskForButton = MouseEvent.getMaskForButton(InputEvent.BUTTON1_MASK);
        // System.out.println(maskForButton);
        // int numberOfButtons = MouseInfo.getNumberOfButtons();
        // System.out.println(numberOfButtons);
        // robot.mouseMove(width/2+100, height/2+100);
        // robot.mouseMove(1004,480);
        // robot.keyPress(56);
        // 获取屏幕分辨率
        // System.out.println(d);
    }
}
