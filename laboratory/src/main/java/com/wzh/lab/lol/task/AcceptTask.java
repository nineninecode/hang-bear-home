package com.wzh.lab.lol.task;

import com.wzh.lab.lol.Params;
import com.wzh.lab.utils.ImageUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/28 21:49
 */
public class AcceptTask implements Runnable {

    @Override
    public void run() {

        while (true) {
            String content = ImageUtils.getContent(Params.acceptRectangle);
            boolean accept = content.contains("接受");
            if (accept) {
                break;
            }
            try {
                // 睡眠2s
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
