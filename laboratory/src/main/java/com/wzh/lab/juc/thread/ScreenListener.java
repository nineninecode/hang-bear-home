package com.wzh.lab.juc.thread;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.wzh.lab.utils.WinScreenUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 22:12
 */
@Slf4j
public class ScreenListener implements Runnable {
    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    private final static User32 LIB = User32.INSTANCE;

    /**
     * 键位按下
     */
    private final static int KEY_PRESS = 256;
    /**
     * 键位弹起
     */
    private final static int KEY_RELEASE = 257;

    /**
     * 截图键int值，如d:68,下箭头：40
     */
    private int screenKeyInt = 68;

    /**
     * 截图保存文件夹
     */
    private String resourcePath = "D:/lab/img/";

    public ScreenListener() {}

    public ScreenListener(int screenKeyInt, String resourcePath) {
        this.screenKeyInt = screenKeyInt;
        this.resourcePath = resourcePath;
    }

    @Override
    public void run() {

        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        keyboardHook = (nCode, wParam, info) -> {
            log.info("screenKeyInt {}", screenKeyInt);
            log.info("wParam {},compare {}", wParam, wParam.intValue() == KEY_PRESS);
            int vkCode = info.vkCode;
            log.info("vkCode {}", vkCode);
            // 按一次键位只执行一次
            if (vkCode == screenKeyInt && wParam.intValue() == KEY_PRESS) {
                // 棋子截图
                screenPiece();
            }
            return LIB.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
        };
        hhk = LIB.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = LIB.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            } else {
                System.err.println("got message");
                LIB.TranslateMessage(msg);
                LIB.DispatchMessage(msg);
            }
        }
        LIB.UnhookWindowsHookEx(hhk);
    }

    /**
     * 棋子截图
     */
    private void screenPiece() {
        ScreenPieceData screenPieceData = new ScreenPieceData();
        List<Point> imagePoints = screenPieceData.getImagePoints();
        int imgWidth = screenPieceData.getImgWidth();
        int imgHeight = screenPieceData.getImgHeight();

        for (Point point : imagePoints) {
            long id = IdUtil.getSnowflake(1, 1).nextId();
            String path = resourcePath + id + ".png";
            BufferedImage screenShot = WinScreenUtils.getScreenShot(point.x, point.y, imgWidth, imgHeight);
            BufferedOutputStream out = null;
            try {
                File file = new File(resourcePath);
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                    if (!mkdirs) {
                        throw new IOException("文件夹创建失败！");
                    }
                }
                out = new BufferedOutputStream(new FileOutputStream(path));
                ImageIO.write(screenShot, "PNG", out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String resourcePath = "D:/lab/img/";
        ScreenListener keyHook = new ScreenListener(40, resourcePath);
        ScreenListener keyHook2 = new ScreenListener(38, resourcePath);
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(keyHook);
        // threadPoolExecutor.execute(keyHook2);
        log.info("screen piece thread started!");
    }
}
