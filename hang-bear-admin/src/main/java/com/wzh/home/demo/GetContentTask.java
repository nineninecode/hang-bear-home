package com.wzh.home.demo;

import java.util.concurrent.Callable;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 9:07
 */
public class GetContentTask implements Callable<String> {

    private String name;

    private Integer sleepTimes;

    public GetContentTask(String name, Integer sleepTimes) {
        this.name = name;
        this.sleepTimes = sleepTimes;
    }

    @Override
    public String call() throws Exception {
        // 假设这是一个比较耗时的操作
        Thread.sleep(sleepTimes * 1000);
        return "this is content : hello " + this.name;
    }

}
