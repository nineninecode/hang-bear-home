package com.wzh.lab;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.wzh.lab.utils.WinScreenUtils;
import com.wzh.lab.win.KeyboardHook;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 15:36
 */
@Slf4j
public class LabApplication {
    public static void main(String[] args) {

        // 人物图片截图开始坐标列表
        List<Point> imagePoints = new ArrayList<>();

        // 桌面缩放比例，基准为96
        BigDecimal baseScreen = new BigDecimal(96);
        BigDecimal screenResolution = new BigDecimal(Toolkit.getDefaultToolkit().getScreenResolution());
        BigDecimal ratio = screenResolution.divide(baseScreen, 2, BigDecimal.ROUND_HALF_UP);

        // 起始位置x:960,y:1855，x每次增加403
        int imgNum = 5;
        for (int i = 0; i < imgNum; i++) {
            imagePoints.add(new Point(960 + 403 * i, 1855));
        }

        int imgWidth = 385;
        int imgHeight = 290;

        final int[] imgCount = {10};

        WinUser.LowLevelKeyboardProc keyboardListener = (nCode, wParam, info) -> {

            log.info("thread name {}", Thread.currentThread().getName());
            // d:68,下箭头：40
            int vkCode = info.vkCode;
            log.info("vkCode {}", vkCode);
            int dInt = 40;
            if (vkCode == dInt) {
                for (Point point : imagePoints) {
                    String path = "D:/lab/img/";
                    path =
                        new StringBuilder().append(path).append("pic-").append(imgCount[0]).append(".png").toString();
                    imgCount[0]++;
                    BufferedImage screenShot = WinScreenUtils.getScreenShot(point.x, point.y, imgWidth, imgHeight);
                    BufferedOutputStream out = null;
                    try {
                        out = new BufferedOutputStream(new FileOutputStream(path));
                        ImageIO.write(screenShot, "PNG", out);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return User32.INSTANCE.CallNextHookEx(null, nCode, wParam, info.getPointer());
        };
        KeyboardHook keyHook = new KeyboardHook(keyboardListener);
        Thread keyHookThread = new Thread(keyHook);
        keyHookThread.start();
        log.info("keyHook start!");
    }
}
