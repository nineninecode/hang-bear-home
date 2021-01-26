package com.wzh.lab.lol.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 截图棋子，台式LG
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/21 9:54
 */
@Slf4j
@Data
public class ScreenPieceData {

    /**
     * 人物棋子图片截图开始坐标列表
     */
    private List<Point> imagePoints = new ArrayList<>();

    /**
     * 图片宽度
     */
    private int imgWidth = 280;
    // private int imgWidth = 385;

    /**
     * 图片高度
     */
    private int imgHeight = 50;
    // private int imgHeight = 290;

    public ScreenPieceData() {
        // 起始位置x:960,y:1855，x每次增加403
        int imgNum = 5;
        for (int i = 0; i < imgNum; i++) {
            this.imagePoints.add(new Point(965 + 403 * i, 2085));
            // this.imagePoints.add(new Point(960 + 403 * i, 1855));
            // this.imagePoints.add(new Point(260 + 403 * i, 455));
        }
    }

}
