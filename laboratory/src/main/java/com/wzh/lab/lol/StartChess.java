package com.wzh.lab.lol;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wzh.lab.lol.thread.OcrThread;
import com.wzh.lab.lol.thread.ScreenListener;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/24 10:42
 */
public class StartChess {
    public static void main(String[] args) {
        ScreenListener screenListener = new ScreenListener();
        OcrThread ocrThread = new OcrThread();
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(screenListener);
        threadPoolExecutor.execute(ocrThread);
    }
}
