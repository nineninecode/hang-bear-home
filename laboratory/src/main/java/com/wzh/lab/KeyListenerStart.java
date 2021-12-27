package com.wzh.lab;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.wzh.lab.win.KeyboardHook;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 15:36
 */
@Slf4j
public class KeyListenerStart {
    public static void main(String[] args) throws Exception {

        WinUser.LowLevelKeyboardProc keyboardListener = (nCode, wParam, info) -> {

            int vkCode = info.vkCode;
            int oneVkCode = 49;
            int twoVkCode = 50;
            int escVkCode = 27;
            int pgUpVkCode = 33;
            int spaceVkCode = 32;
            try {
                Robot robot = new Robot();
                // 只监听按下
                if (wParam.intValue() == WinUser.WM_KEYUP) {
                    if (vkCode == oneVkCode) {
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                        robot.keyPress(KeyEvent.VK_BACK_SPACE);
                        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    }
                    if (vkCode == twoVkCode) {
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_UP);
                        robot.keyPress(KeyEvent.VK_BACK_SPACE);
                        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    }
                    if (vkCode == escVkCode) {
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_M);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_M);
                    }
                }

            } catch (AWTException e) {
                e.printStackTrace();
            }
            return User32.INSTANCE.CallNextHookEx(null, nCode, wParam, info.getPointer());
        };
        KeyboardHook keyHook = new KeyboardHook(keyboardListener);
        Thread keyHookThread = new Thread(keyHook);
        keyHookThread.start();
        log.info("keyHook start!");
    }
}
