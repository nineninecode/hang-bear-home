package com.wzh.home.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LogInventory implements Serializable {

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
     * 云集单据编号
     */
    private String orderNo;

    /**
     * 系统功能 ：1.收货单,2:上架单,3:波次，4:拣货下架,5:出库复核,6:接口
     */
    private Integer actionType;

    /**
     * 变动类型，1：占用,2：释放,3：扣减,4：增加,5：冻结,6：解冻
     */
    private List<Integer> changeType;

    /**
     * 库存类型
     */
    private String inventoryType;

    /**
     * 残次等级
     */
    private String brokenGrade;

    /**
     * 生产日期
     */
    private Date manufactureDate;

    /**
     * 容器编码(现在记录的是托盘号)
     */
    private String containerCode;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 货位
     */
    private String locationCode;

    /**
     * 变化前实物规格库存
     */
    private Integer beforeStockQty;

    /**
     * 实物规格库存变动量，入库为正数标记，出库为负数标记
     */
    private Integer changeStockQty;

    /**
     * 变化后实物规格库存
     */
    private Integer afterStockQty;

    /**
     * 变化前占用规格库存
     */
    private Integer beforeLockedQty;

    /**
     * 占用库规格存变动量，入库为正数标记，出库为负数标记
     */
    private Integer changeLockedQty;

    /**
     * 变化后占用规格库存
     */
    private Integer afterLockedQty;

    /**
     * 变化前冻结规格库存
     */
    private Integer beforeFrozenQty;

    /**
     * 冻结规格库存变动量，入库为正数标记，出库为负数标记
     */
    private Integer changeFrozenQty;

    /**
     * 变化后冻结规格库存
     */
    private Integer afterFrozenQty;

    /**
     * 变动时间
     */
    private Date changeTime;

}
