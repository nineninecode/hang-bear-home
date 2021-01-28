package com.wzh.lab.juc;

import java.util.concurrent.CountDownLatch;

import com.wzh.lab.juc.thread.ScreenPieceData;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 17:09
 */
public class Param {
    public static String[] names = new String[5];

    public static CountDownLatch pieceCount = new CountDownLatch(5);

    public static ScreenPieceData screenPieceData = new ScreenPieceData();

}
