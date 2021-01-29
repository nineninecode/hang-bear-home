package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

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
public class BloodTask extends RectangleTask {

    private int index;
    private final static String numStr = "^[0-9]*$";

    public BloodTask(Rectangle rectangle, int index) {
        super(rectangle);
        this.index = index;
    }

    @Override
    public Boolean call() throws Exception {
        String content = ImageUtils.getContent(super.getRectangle());
        Params.freshBloods.set(index, content);
        if (content != null && !"".equals(content.trim())) {
            if (content.matches(numStr)) {
                Params.blood = Integer.parseInt(content);
            }
        }
        return true;
    }
}
