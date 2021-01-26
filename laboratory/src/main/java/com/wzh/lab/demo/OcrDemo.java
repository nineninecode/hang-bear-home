package com.wzh.lab.demo;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/18 9:24
 */
public class OcrDemo {

    public final static String DATA_PATH = "";
    public final static String DEFAULT_LANG = "";

    /**
     * 根据图片文件进行识别
     *
     * @param imageFile
     *            图片文件
     * @param lang
     *            指定语言库
     * @return 识别文本信息
     */
    public static String doOCRFromFile(File imageFile, String lang) throws Exception {
        ITesseract instance = new Tesseract();
        String path = OcrDemo.class.getClassLoader().getResource("").getPath();
        path = path + "/tessdata";
        System.out.println(path);
        path = path.substring(1);
        // 指定语言库目录
        instance.setDatapath(path);
        instance.setTessVariable("user_defined_dpi", "300");
        instance.setLanguage(lang);
        String result = instance.doOCR(imageFile);
        return result;
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        // SpringApplication.run(OxOcrApp.class, args); //测试不用启用springboot
        System.out.println("OxOcrApp 已启动");
        try {
            // 设置训练库的位置
            // OcrDemo.DATA_PATH = System.getProperty("user.dir") + "/config";
            // OcrDemo.DEFAULT_LANG = "eng"; // eng ：英文 chi_sim ：简体中文
            String property = System.getProperty("user.dir");
            System.out.println(property);
            String lang = "chi_sim";

            String fileName = "ocr5.png";
            String filePath = "d:/" + fileName;
             filePath = "d:/lab/img/1/4.png";
            // 指定要识别的图片文件
            String ocrText = OcrDemo.doOCRFromFile(new File(filePath), lang);
            // OCR识别结果：图像转文本
            System.out.println("识别文本信息：" + ocrText);

            // testCapture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
