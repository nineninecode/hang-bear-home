package com.wzh.lab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/18 10:50
 */
public class ImageMatchDemo {
    /**
     * 裁剪图片
     * @param source:待处理的图片流
     * @param startX:开始x坐标
     * @param startY:开始y坐标
     * @param endX:结束x坐标
     * @param endY:结束y坐标
     * @return BufferedImage
     */
    private BufferedImage imCrop(BufferedImage source, int startX, int startY, int endX, int endY) {
        if(null == source){
            return null;
        }

        int width = source.getWidth();
        int height = source.getHeight();

        if (startX <= -1) {
            startX = 0;
        }
        if (startY <= -1) {
            startY = 0;
        }
        if (endX <= -1) {
            endX = width - 1;
        }
        if (endY <= -1) {
            endY = height - 1;
        }

        if(endY > height){
            endY = height;
        }

        if(endX > width){
            endX = width;
        }

        int tWidth = endX - startX;
        int tHeight = endY - startY;
        if(tWidth < 1 || tHeight < 1){
            System.err.println("imCrop()剪切后的图片大小为0");
            return null;
        }

        BufferedImage result = new BufferedImage(endX - startX, endY - startY , source.getType());
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int rgb = source.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 根据BufferedImage获取图片RGB数组
     * @param bfImage:原图片
     * @return int[][]
     */
    private int[][] getImageGRB(BufferedImage bfImage) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][] result = new int[height][width];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                //使用getRGB(w, h)获取该点的颜色值是ARGB，而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，即bufImg.getRGB(w, h) & 0xFFFFFF。
                result[h][w] = bfImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
    }

    /**
     * 在已标记的图片里抠出标记框中的图块。首先定位标记框，然后把标记框中的图块剪切出来
     * @param vLabeledImg：已标记的图片
     * @param vLabelColor:标记框颜色
     * @return BufferedImage
     */
    private BufferedImage getLabelPatch(BufferedImage vLabeledImg, int vLabelColor){
        if(null == vLabeledImg){
            System.err.println("getLabelPatch(), 原图片为空");
            return null;
        }
        String tag = "getLabelPatch()";
        int tBigWidth = vLabeledImg.getWidth();
        int tBigHeight = vLabeledImg.getHeight();

        //y方向标记框颜色像素计数数组
        int tYcnt[] = new int[tBigHeight];

        //x方向标记框颜色像素计数数组
        int tXcnt[] = new int[tBigWidth];

        //遍历屏幕截图像素点数据，如果碰到标记框颜色，往水平和垂直方向投影
        for(int y=0; y<tBigHeight; y++) {
            for(int x=0; x<tBigWidth; x++) {
                if((vLabelColor ^ vLabeledImg.getRGB(x, y)) == 0){
                    tYcnt[y]++; tXcnt[x]++;
                }
            }
        }

        //确定矩形框的位置
        int tLeft = 0;
        int tRight = 0;
        int tTop = 0;
        int tBottom = 0;

        //数值阈值，比如水平投影至少要10个点才认为这个是一条水平线
        int tNumThreshold = 10;
        for(int y=0; y<tBigHeight; y++){
            if(tYcnt[y] < tNumThreshold){
                continue;
            }
            //考虑到标记框可能不止1个像素宽，要找到标记框除去笔迹宽后的内部的坐标
            //System.out.println(tag + ", y="+y + ", tYcnt[y]="+tYcnt[y]);
            if(tYcnt[y] > tYcnt[y-1] + tNumThreshold){
                tBottom = y-1;
            }else if(tYcnt[y] + tNumThreshold < tYcnt[y-1]){
                tTop = y;
            }

        }

        for(int x=0; x<tBigWidth; x++){
            if(tXcnt[x] < tNumThreshold){
                continue;
            }

            if(tXcnt[x] > tXcnt[x-1] + tNumThreshold){
                tRight = x-1;
            }else if(tXcnt[x] + tNumThreshold < tXcnt[x-1]){
                tLeft = x;
            }
        }

        if(tRight - tLeft < 1 || tBottom - tTop < 1){
            System.err.println("没有检测到颜色框");
            return null;
        }

        return imCrop(vLabeledImg, tLeft, tTop, tRight, tBottom);
    }

    /**
     * 检测源图像中是否包含图像块
     * @param vSrcImgPath：源图片
     * @param vPatchImgPath：图像块
     * @return Boolean
     */
    public Boolean isPatchExist(String vSrcImgPath, String vPatchImgPath) {
        BufferedImage tSrcImg;
        File tFile = new File(vSrcImgPath);
        if(!tFile.exists()){
            System.err.println("源文件不存在:"+vSrcImgPath);
            return false;
        }
        try {
            tSrcImg = ImageIO.read(tFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        tFile = new File(vPatchImgPath);
        if(!tFile.exists()){
            System.err.println("图像块文件不存在:"+vPatchImgPath);
            return false;
        }

        BufferedImage tPatchImg;
        try {
            tPatchImg = ImageIO.read(tFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Boolean tExist = isImageContain(tSrcImg, tPatchImg);
        //回收内存
        System.gc();
        return tExist;
    }

    /**
     * 检测源图片中是否包含标记图片中的图像块
     * @param vSrcImgPath：源图片
     * @param vLabelImgPath：有标记框的图片
     * @param vLabelColor：标记框颜色
     * @return Boolean
     */
    public Boolean isLabelPatchExist(String vSrcImgPath, String vLabelImgPath, int vLabelColor) {
        BufferedImage tSrcImg;
        File tFile = new File(vSrcImgPath);
        if(!tFile.exists()){
            System.err.println("源文件不存在:"+vSrcImgPath);
            return false;
        }
        try {
            tSrcImg = ImageIO.read(tFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        tFile = new File(vLabelImgPath);
        if(!tFile.exists()){
            System.err.println("标记文件不存在:"+vLabelImgPath);
            return false;
        }

        BufferedImage tPatchImg;
        try {
            tPatchImg = ImageIO.read(tFile);
            tPatchImg = getLabelPatch(tPatchImg, vLabelColor);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Boolean tExist = isImageContain(tSrcImg, tPatchImg);
        //回收内存
        System.gc();
        return tExist;
    }

    /**
     * 图片中是否包含一个小图片？
     * @param vBigImg:大图片
     * @param vSmallImg：小图片
     * @return Boolean
     */
    private Boolean isImageContain(BufferedImage vBigImg, BufferedImage vSmallImg) {
        if(null == vBigImg || null == vSmallImg){
            return false;
        }
        int tBigWidth = vBigImg.getWidth();
        int tBigHeight = vBigImg.getHeight();
        int tSmallWidth = vSmallImg.getWidth();
        int tSmallHeight = vSmallImg.getHeight();

        int[][] tBigImgData = getImageGRB(vBigImg);
        int[][] tSmallImgData = getImageGRB(vSmallImg);

        boolean isFinded;

        //遍历屏幕截图像素点数据
        for(int y=0; y<tBigHeight-tSmallHeight+1; y++) {
            for(int x=0; x<tBigWidth-tSmallWidth+1; x++) {
                //根据目标图的尺寸，得到目标图四个角映射到屏幕截图上的四个点，
                //判断截图上对应的四个点与图B的四个角像素点的值是否相同，
                //如果相同就将屏幕截图上映射范围内的所有的点与目标图的所有的点进行比较。
                if((tSmallImgData[0][0]^tBigImgData[y][x])==0
                        && (tSmallImgData[0][tSmallWidth-1]^tBigImgData[y][x+tSmallWidth-1])==0
                        && (tSmallImgData[tSmallHeight-1][tSmallWidth-1]^tBigImgData[y+tSmallHeight-1][x+tSmallWidth-1])==0
                        && (tSmallImgData[tSmallHeight-1][0]^tBigImgData[y+tSmallHeight-1][x])==0) {
                    System.out.println("找到一个匹配点:"+x+","+y);

                    isFinded = isMatchAll(tBigImgData, tSmallImgData, tBigWidth, tBigHeight, tSmallWidth, tSmallHeight,  y, x);
                    //如果比较结果完全相同，则说明图片找到，填充查找到的位置坐标数据到查找结果数组。
                    if(isFinded) {
                        //System.out.println("isImageContain(),匹配");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断屏幕截图上目标图映射范围内的全部点是否全部和小图的点一一对应。
     * @param y - 与目标图左上角像素点想匹配的屏幕截图y坐标
     * @param x - 与目标图左上角像素点想匹配的屏幕截图x坐标
     * @return Boolean
     */
    private Boolean isMatchAll(int vBigData[][], int vSmallData[][], int vBigWidth, int vBigHeight, int vSmallWidth,
                               int vSmallHeight, int y, int x) {
        int biggerY;
        int biggerX;
        int xor;
        for(int smallerY=0; smallerY<vSmallHeight; smallerY++) {
            biggerY = y+smallerY;
            for(int smallerX=0; smallerX<vSmallWidth; smallerX++) {
                biggerX = x+smallerX;
                if(biggerY>=vBigHeight || biggerX>=vBigWidth) {
                    return false;
                }
                xor = vSmallData[smallerY][smallerX]^vBigData[biggerY][biggerX];
                if(xor!=0) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        //原图片
        String tSrcImgPath = "D:/img/full.png";
        //有标记框的图片
        String tLabelImgPath = "D:/img/part.png";

        tSrcImgPath = "D:/img/ocr4.jpg";
        //有标记框的图片
         tLabelImgPath = "D:/img/ocr5.png";

        Boolean tExist1 = new ImageMatchDemo().isPatchExist(tSrcImgPath,tLabelImgPath);

        System.out.println("tExist="+tExist1 );
    }

}
