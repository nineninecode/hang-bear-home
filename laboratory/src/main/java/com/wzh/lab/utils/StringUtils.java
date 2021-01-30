package com.wzh.lab.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 自定义的string工具类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/30 8:12
 */
public class StringUtils {

    /**
     * 换行符正则
     */
    private final static String lineBreakRegex = "\t|\r|\n";

    /**
     * 去除字符串中的换行符
     * 
     * @param content
     *            字符串
     * @return 结果字符串
     */
    public static String replaceBlank(String content) {
        if (Objects.isNull(content)) {
            return "";
        }
        Pattern p = Pattern.compile(lineBreakRegex);
        Matcher m = p.matcher(content);
        content = m.replaceAll("");
        return content;
    }
}
