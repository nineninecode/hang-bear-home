package com.wzh.home.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/5/13 14:03
 */
@Data
public class ItemVo {

    /**
     * 货品编码
     */
    private String itemCode;

    /**
     * 货品条码
     */
    private String barcode;

    /**
     * 仓库编号
     */
    private String warehouseCode;

    /**
     * 货主货品编码
     */
    private String ownerItemCode;

    /**
     * 货主
     */
    private String ownerCode;

    /**
     * 出货数量
     */
    private Integer shippingQty;

    private Date changeTime;
}
