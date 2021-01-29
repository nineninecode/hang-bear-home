package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.concurrent.Callable;


import com.wzh.lab.lol.Params;
import com.wzh.lab.utils.ImageUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/29 14:21
 */
@Slf4j
public class PieceTask implements Callable<Boolean> {

    /**
     * 识别图片矩形
     */
    private Rectangle rectangle;

    private int index;

    public PieceTask(Rectangle rectangle, int index) {
        super();
        this.rectangle = rectangle;
        this.index = index;
    }

    @Override
    public Boolean call() throws Exception {
        String content = ImageUtils.getContent(rectangle);
        Params.freshPieces.set(index, content);
        return true;
    }
}
