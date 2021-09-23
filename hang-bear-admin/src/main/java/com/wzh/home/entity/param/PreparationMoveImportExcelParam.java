package com.wzh.home.entity.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 备货移库单Excel导入-实体类
 * </p>
 *
 * @author weizhuohang
 * @since 2021/5/20 18:57
 */
@Data
public class PreparationMoveImportExcelParam implements IExcelModel, IExcelDataModel {

    private static final long serialVersionUID = -351788409696576756L;

    private String errorMsg;

    private int rowNum;

    /**
     * 货主编码
     */
    @Excel(name = "货主编码")
    @NotEmpty
    private String ownerCode;

    /**
     * 货品条码
     */
    @Excel(name = "货品条码")
    @NotEmpty(message = "货品条码不能为空！")
    private String barcode;

    /**
     * 备货件数
     */
    @Excel(name = "备货件数")
    @Pattern(regexp = "^[+]{0,1}(\\d+)$", message = "备货件数必须为整数！")
    // @Digits(integer = 8, fraction = 0,message = "货品条码不能为空！")
    // @NotNull
    private String stockUpQty;
    // @Min(value = 0,message = "货品条码不能为空！")
    // private Integer stockUpQty;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

}
