package com.wzh.lab.juc;

import lombok.extern.slf4j.Slf4j;


import com.wzh.lab.lol.service.OcrService;

/**
 * <p>
 * 棋子ocr线程
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 11:10
 */
@Slf4j
public class PieceOcrThread extends Thread {

    /**
     * 识别图片路径
     */
    private int index;

    private final OcrService ocrService;

    public PieceOcrThread(OcrService ocrService, int index) {
        super();
        this.ocrService = ocrService;
        this.index = index;
    }

    @Override
    public void run() {
        while (true) {
            //ScreenPieceData screenPieceData = Param.screenPieceData;
            //List<Point> imagePoints = screenPieceData.getImagePoints();
            //Point point = imagePoints.get(index);
            //
            //BufferedImage screenShot = WinScreenUtils.getScreenShot(point.x, point.y, screenPieceData.getImgWidth(),
            //    screenPieceData.getImgHeight());
            //String s = ocrService.doOcr(screenShot);
            //Param.names[index] = s;
            //Param.pieceCount.countDown();
        }
    }
}
