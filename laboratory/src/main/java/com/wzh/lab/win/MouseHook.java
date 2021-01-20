package com.wzh.lab.win;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * <p>
 * windows mouse hook
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 9:35
 */
public class MouseHook {
    /**
     * 鼠标事件编码
     */
    public static final int WM_MOUSE_MOVE = 512;
    public static final int WM_LEFT_BUTTON_DOWN = 513;
    public static final int WM_LEFT_BUTTON_UP = 514;
    public static final int WM_RIGHT_BUTTON_DOWN = 516;
    public static final int WM_RIGHT_BUTTON_UP = 517;
    public static final int WM_MID_BUTTON_DOWN = 519;
    public static final int WM_MID_BUTTON_UP = 520;
    public User32 lib;
    private static WinUser.HHOOK hhk;
    private MouseHookListener mouseHook;
    private WinDef.HMODULE hMod;
    private boolean isWindows = false;

    public MouseHook() {
        isWindows = Platform.isWindows();
        if (isWindows) {
            lib = User32.INSTANCE;
            hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        }

    }

    /**
     * 添加钩子监听
     * 
     * @param mouseHook
     *            listener
     */
    public void addMouseHookListener(MouseHookListener mouseHook) {
        this.mouseHook = mouseHook;
        this.mouseHook.lib = lib;
    }

    /**
     * 启动
     */
    public void startWindowsHookEx() {
        if (isWindows) {
            lib.SetWindowsHookEx(WinUser.WH_MOUSE_LL, mouseHook, hMod, 0);
            int result;
            WinUser.MSG msg = new WinUser.MSG();
            while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
                if (result == -1) {
                    System.err.println("error in get message");
                    break;
                } else {
                    System.err.println("got message");
                    lib.TranslateMessage(msg);
                    lib.DispatchMessage(msg);
                }
            }
        }

    }

    /**
     * 关闭
     */
    public void stopWindowsHookEx() {
        if (isWindows) {
            lib.UnhookWindowsHookEx(hhk);
        }
    }

    public static void main(String[] args) {
        try {
            MouseHook mouseHook = new MouseHook();
            mouseHook.addMouseHookListener(new MouseHookListener());
            mouseHook.startWindowsHookEx();

            Thread.sleep(20000);
            mouseHook.stopWindowsHookEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
