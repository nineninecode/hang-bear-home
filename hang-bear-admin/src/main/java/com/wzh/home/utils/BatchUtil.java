package com.wzh.home.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 批量处理工具类
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/17 15:34
 */
public class BatchUtil {

    /**
     * 获取分批后的列表
     * 
     * @param executeList
     *            处理列表
     * @param limit
     *            分组大小
     * @param <T>
     *            泛型
     * @return 分批结果列表
     */
    public static <T> List<List<T>> getBatchedList(List<T> executeList, Integer limit) {
        List<List<T>> batchedList = new ArrayList<>();
        if (executeList == null || executeList.size() == 0) {
            return batchedList;
        }
        int executeSize = executeList.size();
        for (int from = 0, to = 0; from < executeSize; from = to) {
            to = Math.min(from + limit, executeSize);
            List<T> objects = executeList.subList(from, to);
            batchedList.add(objects);
        }
        return batchedList;
    }

    /**
     * 列表分批执行
     * 
     * @param executeList
     *            需要执行的列表
     * @param limit
     *            分批大小
     * @param consumer
     *            列表执行对象
     * @param <T>
     *            泛型
     */
    public static <T> void executedBatchList(List<T> executeList, Integer limit, Consumer<List<T>> consumer) {

        if (executeList == null || executeList.size() == 0) {
            return;
        }
        int executeSize = executeList.size();
        for (int from = 0, to = 0; from < executeSize; from = to) {
            to = Math.min(from + limit, executeSize);
            List<T> objects = executeList.subList(from, to);
            consumer.accept(objects);
        }
    }
}
