package com.wzh.lab.lol.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 棋子
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/21 16:19
 */
@Data
public class Piece {

    /**
     * 图片指纹
     */
    private String imageCode;

    /**
     * 棋子名称
     */
    private String pieceName;

    /**
     * 棋子属性，阵营和职业
     */
    private List property;

    /**
     * 棋子花费
     */
    private Integer cost;

    /**
     * 通过图片指纹判断是否为改棋子
     * 
     * @param imageSign
     *            图片指纹
     * @return 结果
     */
    public Boolean isSame(String imageSign) {
        Boolean result = Boolean.FALSE;
        int bitCount = new BigInteger(imageCode, 2).xor(new BigInteger(imageSign, 2)).bitCount();
        int diffMax = 6;
        if (bitCount < diffMax) {
            result = Boolean.TRUE;
        }
        return result;
    }

}
