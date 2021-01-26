package com.wzh.lab.lol.service.impl;

import cn.hutool.core.util.IdUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import com.wzh.lab.lol.thread.ScreenPieceData;
import com.wzh.lab.lol.service.ChessService;
import com.wzh.lab.utils.WinScreenUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/24 10:24
 */
public class ChessServiceImpl implements ChessService {

    @Override
    public void screenPieces() {
        ScreenPieceData screenPieceData = new ScreenPieceData();
        List<Point> imagePoints = screenPieceData.getImagePoints();
        int imgWidth = screenPieceData.getImgWidth();
        int imgHeight = screenPieceData.getImgHeight();
        String resourcePath = "D:/lab/img/";

        for (Point point : imagePoints) {
            long id = IdUtil.getSnowflake(1, 1).nextId();
            String path = resourcePath + id + ".png";
            BufferedImage screenShot = WinScreenUtils.getScreenShot(point.x, point.y, imgWidth, imgHeight);
            BufferedOutputStream out = null;
            try {
                File file = new File(resourcePath);
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                    if (!mkdirs) {
                        throw new IOException("文件夹创建失败！");
                    }
                }
                out = new BufferedOutputStream(new FileOutputStream(path));
                ImageIO.write(screenShot, "PNG", out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
