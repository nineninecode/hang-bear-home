package com.wzh.home.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.wzh.home.entity.param.PreparationMoveImportExcelParam;
import com.wzh.home.entity.po.UmsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 简单的前端控制器
 * </p>
 *
 * @author weizhuohang
 * @since 2020/10/30 15:14
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping("/say")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/hi")
    public UmsUser sayHi() {
        UmsUser user = new UmsUser();
        user.setUsername("wzh");
        user.setNickName("韦卓航");
        return user;
    }

    /**
     * easy poi excel import
     * 
     * @param file
     *            file
     * @return excel data
     */
    @PostMapping("/import")
    public List<PreparationMoveImportExcelParam> importExcel(@RequestParam("file") MultipartFile file) {
        List<PreparationMoveImportExcelParam> params = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            log.info("文件名{}", file.getOriginalFilename());
            // 导入参数设置
            ImportParams importParams = new ImportParams();
            // easy-poi解析
            try {
                importParams.setNeedVerfiy(true);
                ExcelImportResult<PreparationMoveImportExcelParam> objectExcelImportResult =
                    ExcelImportUtil.importExcelMore(inputStream, PreparationMoveImportExcelParam.class, importParams);
                params = objectExcelImportResult.getList();
                params.addAll(objectExcelImportResult.getFailList());
                // params = ExcelImportUtil.importExcel(inputStream, PreparationMoveImportExcelParam.class,
                // importParams);
                log.info("导入结果 {}", params);
            } catch (Exception e) {
                log.error("easy-poi解析错误", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }
}
