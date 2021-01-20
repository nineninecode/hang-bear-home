package com.wzh.lab.win;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 10:14
 */
public class MouseHookStruct extends Structure {

    public static class ByReference extends MouseHookStruct implements Structure.ByReference {};

    public int wHitTestCode;
    /**
     * 点坐标
     */
    public WinUser.POINT pt;

    /**
     * 窗口句柄
     */
    public HWND hwnd;

    /**
     * 扩展信息
     */
    public ULONG_PTR dwExtraInfo;

    /**
     * width
     */
    public int vkCode;
    /**
     * height
     */
    public int scanCode;

    /**
     * 返回属性顺序
     * 
     * @return list
     */
    @Override
    protected List getFieldOrder() {
        return Arrays.asList("dwExtraInfo", "hwnd", "pt", "wHitTestCode", "vkCode", "scanCode");
    }
}