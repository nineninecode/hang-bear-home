package com.wzh.home.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/26 9:09
 */
public class ThreadApplication {
    public static void main(String[] args) {ExecutorService executeService = Executors.newCachedThreadPool();
        List<GetContentTask> taskList = new ArrayList<GetContentTask>();
        long startTime = System.currentTimeMillis();
        for (int i = 0;i < 10;i ++) {
            taskList.add(new GetContentTask("micro" + i, 10));
        }
        try {
            System.out.println("主线程发起异步任务请求");
            List<Future<String>> resultList = executeService.invokeAll(taskList);
            // 这里会阻塞等待resultList获取到所有异步执行的结果才会执行
            for (Future<String> future : resultList) {
                System.out.println(future.get());
            }
            // 主线程假装很忙执行8秒钟
            Thread.sleep(8);
            long endTime = System.currentTimeMillis();
            System.out.println("耗时 : " + (endTime - startTime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
