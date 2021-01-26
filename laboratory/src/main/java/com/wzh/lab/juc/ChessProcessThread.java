package com.wzh.lab.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:12
 */
@Slf4j
public class ChessProcessThread extends Thread {

    private final List<OcrService> ocrServices;
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


    public ChessProcessThread(List<OcrService> ocrServices) {
        super();
        this.ocrServices = ocrServices;
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
                for(OcrService ocrService:ocrServices){
                    ocrService.signalAll();
                }
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

    public static void main(String[] args) {
        OcrService ocrService = new OcrService();
        OcrService ocrService2 = new OcrService();
        List<OcrService> ocrServices= new ArrayList<>();
        ocrServices.add(ocrService);
        ocrServices.add(ocrService2);
        ChessProcessThread chessProcessThread = new ChessProcessThread(ocrServices);
        chessProcessThread.setName("chess process");
        PieceOcrThread pieceOcrThread = new PieceOcrThread(ocrService, "D:/lab/img/1/1.png");
        pieceOcrThread.setName("jax");
        PieceOcrThread pieceOcrThread2 = new PieceOcrThread(ocrService2, "D:/lab/img/1/2.png");
        pieceOcrThread2.setName("delaiesi");

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(pieceOcrThread);
        threadPoolExecutor.execute(pieceOcrThread2);
        threadPoolExecutor.execute(chessProcessThread);
        //ocrService.signalAll();
    }
}
