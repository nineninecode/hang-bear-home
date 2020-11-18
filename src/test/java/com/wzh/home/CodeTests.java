package com.wzh.home;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2020/11/17 10:20
 */
public class CodeTests {
    public static void main(String[] args) {
        // ArrayList<T> objects = Lists.newArrayListWithCapacity(10);
        // batchList();
        Consumer<List<String>> consumer = new Consumer<List<String>>() {

            /**
             * Performs this operation on the given argument.
             *
             * @param list
             *            the input argument
             */
            @Override
            public void accept(List<String> list) {
                System.out.println(list.size());
            }
        };
        List<String> studentList = new ArrayList<>(2000);
        // for (int i = 0; i < 2000; i++) {
        // studentList.add(String.valueOf(i));
        // }
        batchListGoogle(studentList, 800, consumer);
    }

    public static void batchList() {
        int BATCH_SIZE = 1200;
        // 假设有一个List<Student> ,长度为2000条, 并填充好了数据
        List<String> studentList = new ArrayList<String>(2000);
        for (int i = 0; i < 2000; i++) {
            studentList.add(String.valueOf(i));
        }
        System.out.println(studentList.size());
        List<List<String>> allList = new ArrayList<>();
        for (int from = 0, to = 0, size = studentList.size(); from < size; from = to) {
            List<String> pageList = null;
            to = Math.min(from + BATCH_SIZE, size);
            pageList = studentList.subList(from, to);
            // 进行分批处理 pageList;
            allList.add(pageList);
        }
        System.out.println(allList);
        // System.out.println(Arrays.);Arrays
    }

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

    public static <T> void batchListGoogle(List<T> executeList, Integer limit, Consumer<List<T>> consumer) {
        int executeSize = executeList.size();
        for (int from = 0, to = 0; from < executeSize; from = to) {
            to = Math.min(from + limit, executeSize);
            List<T> objects = executeList.subList(from, to);
            consumer.accept(objects);
        }
    }
}
