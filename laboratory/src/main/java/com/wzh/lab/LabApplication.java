package com.wzh.lab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.wzh.lab.utils.WinScreenUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/19 15:36
 */
public class LabApplication {
    public static void main(String[] args) {

        String path = "D:/img/3-pic.png";
        BufferedImage screenShot = WinScreenUtils.getScreenShot(500, 200, 700, 200);

        // 输出流
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            ImageIO.write(screenShot, "PNG", out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
