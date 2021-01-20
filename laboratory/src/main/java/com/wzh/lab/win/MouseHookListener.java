package com.wzh.lab.win;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/20 9:31
 */
public class MouseHookListener implements WinUser.LowLevelKeyboardProc {

    /**
     * window应用程序接口
     */
    public User32 lib = null;
    /**
     * 钩子的句柄
     */
    public WinUser.HHOOK hhk;

    public MouseHookListener() {
        this.lib = User32.INSTANCE;;
    }

    /**
     * 回调,返回这个值链中的下一个钩子程序，返回值的含义取决于钩型
     * 
     * @param nCode
     *            nCode
     * @param wParam
     *            wParam
     * @param lParam
     *            lParam
     * @return 钩子信息
     */
    @Override
    public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT lParam) {
        if (nCode >= 0) {
            if (wParam.intValue() == MouseHook.WM_RIGHT_BUTTON_DOWN) {
                System.out.println("mouse right button down, x=" + lParam.vkCode + " y=" + lParam.scanCode);
            }
            // switch (wParam.intValue()) {
            // case MouseHook.WM_MOUSE_MOVE:
            // // System.err
            // // .println("mouse move left button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);
            // break;
            // case MouseHook.WM_LEFT_BUTTON_DOWN:
            // System.err
            // .println("mouse down left button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);
            // break;
            // case MouseHook.WM_LEFT_BUTTON_UP:
            // System.err.println("mouse up left button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
            // break;
            // case MouseHook.WM_MID_BUTTON_DOWN:
            // System.err.println("mouse mid button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);
            // break;
            // case MouseHook.WM_MID_BUTTON_UP:
            // System.err.println("mouse mid button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
            // break;
            // default:
            // break;
            // }
        }
        // 将钩子信息传递到当前钩子链中的下一个子程，一个钩子程序可以调用这个函数之前或之后处理钩子信息
        // hhk：当前钩子的句柄
        // nCode ：钩子代码; 就是给下一个钩子要交待的，钩传递给当前Hook过程的代码。下一个钩子程序使用此代码，以确定如何处理钩的信息。
        // wParam：要传递的参数; 由钩子类型决定是什么参数，此参数的含义取决于当前的钩链与钩的类型。
        // lParam：Param的值传递给当前Hook过程。此参数的含义取决于当前的钩链与钩的类型。
        return lib.CallNextHookEx(hhk, nCode, wParam, lParam.getPointer());

    }

    //
    /// **
    // * 回调,返回这个值链中的下一个钩子程序，返回值的含义取决于钩型
    // *
    // * @param nCode
    // * @param wParam
    // * @param lParam
    // * @return
    // */
    // public abstract WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, MouseHookStruct lParam);

}
