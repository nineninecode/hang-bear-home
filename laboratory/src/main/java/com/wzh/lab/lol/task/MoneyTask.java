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
 * 棋子ocr线程
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:10
 */
@Slf4j
public class MoneyTask extends RectangleTask {

    private ITesseract instance;
    private final static String numStr = "^[0-9]*$";

    public MoneyTask(Rectangle rectangle) {
        super(rectangle);
        instance = new Tesseract();
        String path = MoneyTask.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        path = path.substring(1);
        // 指定语言库目录
        this.instance.setDatapath(path);
        // 设置分辨率
        this.instance.setTessVariable("user_defined_dpi", "300");
        // 设置识别语言为英文
        this.instance.setLanguage("ara");
    }

    @Override
    public Boolean call() throws Exception {
        Robot robot = new Robot();
        BufferedImage capture = robot.createScreenCapture(super.getRectangle());
        String content = instance.doOCR(capture);
        content = StringUtils.replaceBlank(content);
        log.info("money task {}", content);
        if (content != null && !"".equals(content.trim())) {
            if (content.matches(numStr)) {
                Params.money = Integer.parseInt(content);
            }
        }
        return true;
    }
}
