package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

import org.apache.commons.lang3.StringUtils;

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
public class MoneyTask extends RectangleTask {

    public MoneyTask(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public Boolean call() throws Exception {
        String content = ImageUtils.getContent(super.getRectangle());
        content = StringUtils.isBlank(content) ? "" : content;
        Params.money = Integer.parseInt(content);
        return true;
    }
}
