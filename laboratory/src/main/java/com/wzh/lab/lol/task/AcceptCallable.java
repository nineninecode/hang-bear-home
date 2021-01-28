package com.wzh.lab.lol.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;


import com.wzh.lab.lol.Params;
import com.wzh.lab.utils.ImageUtils;
import com.wzh.lab.utils.WinRobotUtils;

/**
 * <p>
 * 寻找对局，并接受
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/28 21:49
 */
@Slf4j
public class AcceptCallable implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        Boolean accept = false;

        // 点击开始
        WinRobotUtils.leftMouseSinglePress(Params.startIcon);
        while (true) {
            String content = ImageUtils.getContent(Params.acceptRectangle);
            log.info("接受位置数据 {}", content);
            accept = content.contains("接受");
            if (accept) {
                WinRobotUtils.leftMouseSinglePress(Params.acceptIcon);
                break;
            }
            try {
                // 睡眠2s
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return accept;
    }
}
