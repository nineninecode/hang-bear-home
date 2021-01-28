package com.wzh.lab.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzh.lab.lol.enums.PieceEnum;

/**
 * <p>
 * 图片处理工具类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 10:14
 */
public class ImageUtils {

    /**
     * 改变图片的尺寸
     *
     * @param newWidth
     *            宽
     * @param newHeight
     *            高
     * @return 结果
     */
    public static BufferedImage changeSize(int newWidth, int newHeight, BufferedImage image) {

        // 构建图片流
        BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        // 绘制改变尺寸后的图
        tag.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
        return tag;

    }

    /**
     * 灰度图直观地讲就是将原来的RGB图像转换为只有灰度级的图像，把每个像素点的RGB值拿出来，算一下他们的平均值(R+G+B)/3，然后再替换原来的RGB值就OK了。
     * 
     * @param image
     * @return
     */
    public static BufferedImage convertGrayscale(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int p = image.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(i, j, p);
            }
        }

        return image;
    }

    /**
     * 获取图片指纹，64位2进制数
     * 
     * @param image
     *            图片
     * @return 图片指纹
     */
    public static String getImageSign(BufferedImage image) {

        // 8*8图片
        BufferedImage image64 = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        image64.getGraphics().drawImage(image, 0, 0, 8, 8, null);
        image = image64;

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = new int[width * height];
        int pixelSum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int p = image.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                pixels[j * height + i] = p;
                pixelSum = pixelSum + p;
            }
        }
        int pixelAvg = pixelSum / (width * height);
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int flag = 0;
            if (pixel >= pixelAvg) {
                flag = 1;
            }
            stringBuilder.append(flag);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取图片指纹，64位2进制数
     * 
     * @param path
     *            图片路径
     * @return 图片指纹
     */
    public static String getImageSign(String path) {

        BufferedInputStream in = null;
        String imageSign = "";
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            // 字节流转图片对象
            BufferedImage bi = ImageIO.read(in);
            imageSign = getImageSign(bi);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageSign;
    }

    /**
     * 计算两个字符串的汉明距离
     * 
     * @param imageCode
     *            字符串1
     * @param imageSign
     *            字符串2
     * @return 距离
     */
    public static int getHangMing(String imageCode, String imageSign) {

        int bitCount = new BigInteger(imageCode, 2).xor(new BigInteger(imageSign, 2)).bitCount();

        return bitCount;
    }

    /**
     * 判断是否为同一图片
     * 
     * @param imageCode
     *            字符串1
     * @param imageSign
     *            字符串2
     * @param distance
     *            最大距离
     * @return 结果
     */
    public static boolean isSameImage(String imageCode, String imageSign, int distance) {
        boolean result = true;
        int hangMing = getHangMing(imageCode, imageSign);
        if (hangMing > distance) {
            result = false;
        }
        return result;
    }

    /**
     * ocr获取屏幕矩形中的内容
     * 
     * @param rectangle
     *            识别矩形
     * @return 内容
     */
    public static String getContent(Rectangle rectangle) {
        String result = null;
        try {
            Robot robot = new Robot();
            BufferedImage bfImage = robot.createScreenCapture(rectangle);
            result = OcrUtils.doOCR(bfImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    // public static Bitmap convertGreyImg(Bitmap img) {
    // int width = img.getWidth(); // 获取位图的宽
    // int height = img.getHeight(); // 获取位图的高
    //
    // int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
    //
    // img.getPixels(pixels, 0, width, 0, 0, width, height);
    // int alpha = 0xFF << 24;
    // for (int i = 0; i < height; i++) {
    // for (int j = 0; j < width; j++) {
    // int original = pixels[width * i + j];
    // int red = ((original & 0x00FF0000) >> 16);
    // int green = ((original & 0x0000FF00) >> 8);
    // int blue = (original & 0x000000FF);
    //
    // int grey = (int)((float)red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
    // grey = alpha | (grey << 16) | (grey << 8) | grey;
    // pixels[width * i + j] = grey;
    // }
    // }
    // Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    // result.setPixels(pixels, 0, width, 0, 0, width, height);
    // return result;
    // }

    public static void main(String[] args) throws IOException {
        String resourcePath = "D:/lab/img/";

        Map<String, List<Boolean>> resultMap = new HashMap<>(128);
        Map<String, List<Integer>> bitMap = new HashMap<>(128);
        int distance = 5;

        int falseNum = 0;
        int sumNum = 0;

        PieceEnum[] values = PieceEnum.values();
        for (PieceEnum pieceEnum : values) {
            List<Boolean> resultList = new ArrayList<>();
            List<Integer> bitCountList = new ArrayList<>();
            String path = resourcePath + pieceEnum.getCode();
            List<File> files = FileReadUtils.getFiles(path);
            int num = 0;
            String baseSign = "";
            for (File file : files) {
                String imageSign = getImageSign(ImageIO.read(file));
                if (num == 0) {
                    baseSign = imageSign;
                }

                boolean sameImage = true;
                int hangMing = getHangMing(baseSign, imageSign);
                if (hangMing > distance) {
                    sameImage = false;
                    falseNum++;
                }
                bitCountList.add(hangMing);
                resultList.add(sameImage);
                num++;
                sumNum++;
            }
            resultMap.put(pieceEnum.getName(), resultList);
            bitMap.put(pieceEnum.getName(), bitCountList);
        }
        System.out.println(resultMap);
        System.out.println(bitMap);
        System.out.println(falseNum);
        System.out.println(sumNum);

    }
}
