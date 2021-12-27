package com.wzh.lab.win;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;
import java.util.Objects;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 22:12
 */
@Slf4j
public class KeyboardHook implements Runnable {
    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    private final static User32 LIB = User32.INSTANCE;

    public KeyboardHook() {}

    public KeyboardHook(WinUser.LowLevelKeyboardProc keyboardHookImpl) {
        keyboardHook = keyboardHookImpl;
    }

    @Override
    public void run() {

        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        if (Objects.isNull(keyboardHook)) {
            keyboardHook = (nCode, wParam, info) -> {
                log.info("vkCode {},scanCode {},", info.vkCode, info.scanCode);
                return LIB.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            };
        }
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

    public static void main(String[] args) {
        KeyboardHook keyHook = new KeyboardHook();
        Thread keyHookThread = new Thread(keyHook);
        keyHookThread.start();
        log.info("keyHook start!");
        int vkAlt = KeyEvent.VK_CONTROL  ;
        log.info(String.valueOf(vkAlt));
    }
}
