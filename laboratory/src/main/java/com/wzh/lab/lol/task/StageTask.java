package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

import com.wzh.lab.utils.ImageUtils;

/**
 * <p>
 * 棋子ocr线程
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:10
 */
@Slf4j
public class StageTask extends MyLock implements Runnable {

    /**
     * 识别图片矩形
     */
    private Rectangle rectangle;

    public StageTask(Rectangle rectangle) {
        super();
        this.rectangle = rectangle;
    }

    @Override
    public void run() {

        while (true) {
            String content = ImageUtils.getContent(rectangle);
            log.info(content);
            boolean isPrepare = content.contains("备战环节");
            if (isPrepare) {
                super.await();
            }
        }
    }
}
