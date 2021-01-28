package com.wzh.lab.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.wzh.lab.lol.enums.PieceEnum;

/**
 * <p>
 * OCR图像文字识别工具类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/23 23:26
 */
public class OcrUtils {

    private static ITesseract instance = new Tesseract();
    static {
        String path = OcrUtils.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        path = path.substring(1);
        // 指定语言库目录
        instance.setDatapath(path);
        // 设置分辨率
        instance.setTessVariable("user_defined_dpi", "300");
        // 设置识别语言为简体中文
        instance.setLanguage("chi_sim");
    }

    public static String doOCR(File imageFile) {
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doOCR(BufferedImage imageFile) {
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {

        PieceEnum yasuo = PieceEnum.KATLINNA;
        String resourcePath = "D:/lab/img/";
        String path = resourcePath + yasuo.getCode();
        List<File> files = FileReadUtils.getFiles(resourcePath);
        List<String> fileStrs = new ArrayList<>();
        int num = 0;
        long l = System.currentTimeMillis();
        for (File file : files) {
            String result = doOCR(file);
            fileStrs.add(result);
            boolean contains = result.contains(yasuo.getName());
            if (contains) {
                num++;
            }
        }
        System.out.println(num);
        System.out.println(fileStrs);
        System.out.println(System.currentTimeMillis() - l);
    }

}
