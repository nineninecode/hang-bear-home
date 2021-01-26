package com.wzh.lab.juc;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

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
    private String path;

    private final OcrService ocrService;

    public PieceOcrThread(OcrService ocrService, String path) {
        super();
        this.ocrService = ocrService;
        this.path = path;
    }

    @Override
    public void run() {
        File file = new File(path);
        while (true) {
            log.info(path);
            ocrService.doOCR(file);
            Param.end.countDown();
        }
    }
}
