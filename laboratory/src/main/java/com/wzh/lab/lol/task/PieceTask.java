package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.awt.*;
import java.awt.image.BufferedImage;


import com.wzh.lab.lol.Params;
import com.wzh.lab.utils.StringUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/29 14:21
 */
@Slf4j
public class PieceTask extends RectangleTask {

    private int index;

    private ITesseract instance;

    public PieceTask(Rectangle rectangle, int index) {
        super(rectangle);
        this.index = index;
        instance = new Tesseract();
        String path = PieceTask.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        path = path.substring(1);
        // 指定语言库目录
        this.instance.setDatapath(path);
        // 设置分辨率
        this.instance.setTessVariable("user_defined_dpi", "300");
        // 设置识别语言为简体中文
        this.instance.setLanguage("chi_sim");
    }

    @Override
    public Boolean call() throws Exception {
        Robot robot = new Robot();
        BufferedImage capture = robot.createScreenCapture(super.getRectangle());
        String content = instance.doOCR(capture);
        content = StringUtils.replaceBlank(content);
        log.info("PieceTask task:{}", content);
        Params.freshPieces.set(index, content);
        return true;
    }
}
