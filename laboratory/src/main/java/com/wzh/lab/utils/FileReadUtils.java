package com.wzh.lab.utils;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        FileInputStream in;
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

    /**
     * 获取路径下单层文件列表
     * 
     * @param path
     *            文件路径或文件夹路径
     * @return
     */
    public static List<File> getFiles(String path) {
        List<File> files = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] tempList = file.listFiles();
            for (File tempFile : tempList) {
                // 文件
                if (tempFile.isFile()) {
                    files.add(tempFile);
                }
                // 文件夹
                if (tempFile.isDirectory()) {
                    // 这里就不递归了，
                }
            }
        } else {
            files.add(file);
        }

        return files;
    }

    public static void main(String[] args) {
        List<String> resourceFileContent = getResourceFileContent("pieces.txt");
        System.out.println(resourceFileContent);
        List<Piece> pieceFromFile = getPieceFromFile();
        System.out.println(pieceFromFile);

        // String resourcePath = "D:/lab/img/1352289335604547584.png";
        // String resourcePath = "D:/lab/empty";
        String resourcePath = "D:/lab/img/stage/";
        List<File> files = getFiles(resourcePath);
        String baseSign = "";
        int count = 0;
        for (File file : files) {
            try {
                BufferedImage read = ImageIO.read(file);
                String imageSign = ImageUtils.getImageSign(read);
                if (count == 0) {
                    baseSign = imageSign;
                }
                int hangMing = ImageUtils.getHangMing(baseSign, imageSign);
                log.info("文件名 {},指纹 {},汉明 {}", file.getName(), imageSign, hangMing);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
