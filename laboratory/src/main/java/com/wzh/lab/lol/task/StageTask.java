package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

import com.wzh.lab.lol.Params;
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
public class StageTask extends RectangleTask {

    public StageTask(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public Boolean call() throws Exception {
        String content = ImageUtils.getContent(this.getRectangle());
        Params.stage = content;
        Params.isPrepare = content.contains(Params.prepareStage);
        return true;
    }
}
