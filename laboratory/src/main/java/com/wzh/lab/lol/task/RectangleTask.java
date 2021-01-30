package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/29 14:21
 */
@Slf4j
public class RectangleTask implements Callable<Boolean> {

    /**
     * 识别图片矩形
     */
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public RectangleTask(Rectangle rectangle) {
        super();
        this.rectangle = rectangle;
    }

    @Override
    public Boolean call() throws Exception {
        return true;
    }

}
