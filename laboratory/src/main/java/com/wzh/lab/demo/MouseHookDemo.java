package com.wzh.lab.demo;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;

/**
 * @author 12037
 */
public class MouseHookDemo implements Runnable {
    private WinUser.HHOOK hhk;

    // 钩子回调函数
    private WinUser.LowLevelKeyboardProc mouseProc = new WinUser.LowLevelKeyboardProc() {

        @Override
        public LRESULT callback(int nCode, WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
            // 输出按键值和按键时间
            if (nCode >= 0) {
                // System.out.println(wParam);
                // 禁用鼠标右键按下和弹起
                // System.out.println(wParam.intValue());
                if (wParam.intValue() == 517 || wParam.intValue() == 516) {
                    System.out.println("按下右键");
                    // System.exit(0);
                    // return new LRESULT(1);
                }
            }
            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, (Pointer) null);
        }

    };

    @Override
    public void run() {
        setHookOn();
    }

    // 安装钩子
    public void setHookOn() {
        System.out.println("Hook On!");

        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        hhk = User32.INSTANCE.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseProc, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                setHookOff();
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }

    // 移除钩子并退出
    public void setHookOff() {
        System.out.println("Hook Off!");
        User32.INSTANCE.UnhookWindowsHookEx(hhk);
        System.exit(0);
    }

    public static void main(String[] args) {
        MouseHookDemo kbhook = new MouseHookDemo();
        new Thread(kbhook).start();
    }
}