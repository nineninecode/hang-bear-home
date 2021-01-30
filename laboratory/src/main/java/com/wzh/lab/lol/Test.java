package com.wzh.lab.lol;

import lombok.extern.slf4j.Slf4j;


import com.wzh.lab.utils.StringUtils;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/30 7:56
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info("freshBloods {}", Params.freshBloods);
        Params.freshBloods.set(2, "22");
        log.info("freshBloods {}", Params.freshBloods);

        String content = "";
        content = StringUtils.replaceBlank(content);
        log.info("content:{}", content);
    }
}
