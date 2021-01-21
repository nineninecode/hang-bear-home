package com.wzh.lab.utils;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wzh.lab.constant.CommonConstant;
import com.wzh.lab.lol.entity.Piece;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/21 16:49
 */
@Slf4j
public class FileReadUtils {

    /**
     * 从文件中加载所有棋子列表
     * 
     * @return 棋子列表
     */
    public static List<Piece> getPieceFromFile() {
        List<Piece> pieces = new ArrayList<>();
        List<String> strings = getResourceFileContent("pieces.txt");
        for (String string : strings) {
            String[] splits = string.split(";");
            Piece piece = new Piece();
            piece.setImageCode(splits[0]);
            piece.setPieceName(splits[1]);
            piece.setCost(Integer.valueOf(splits[3]));
            String split = splits[2];
            String[] properties = split.split(",");
            List<String> propertyList = Arrays.asList(properties);
            piece.setProperty(propertyList);
            pieces.add(piece);
        }
        return pieces;
    }

    /**
     * 按行读取resource文件内容
     * 
     * @param path
     *            resource中路径
     * @return 行内容列表
     */
    public static List<String> getResourceFileContent(String path) {
        List<String> strings = new ArrayList<>();
        FileInputStream in = null;
        try {
            in = new FileInputStream(CommonConstant.RESOURCE_PATH + path);
            BufferedReader utf8Reader = IoUtil.getUtf8Reader(in);
            String s;
            while ((s = utf8Reader.readLine()) != null) {
                strings.add(s);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strings;
    }

    public static void main(String[] args) {
        List<String> resourceFileContent = getResourceFileContent("pieces.txt");
        System.out.println(resourceFileContent);
        List<Piece> pieceFromFile = getPieceFromFile();
        System.out.println(pieceFromFile);
    }
}
