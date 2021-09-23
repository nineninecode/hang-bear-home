//import com.alibaba.fastjson.JSON;
//import com.wzh.home.entity.po.InventoryChangeParam;
//import com.wzh.home.entity.po.InventoryUniqueParam;
//
//import java.util.*;
//
///**
// * <p>
// *
// * </p>
// *
// * @author weizhuohang
// * @since 2021/06/29 17:00
// */
//public class Test2 {
//    public static void main(String[] args) {
//
//        List<InventoryChangeParam> changeList = new ArrayList<>();
//
//        for (int i = 0; i < 2; i++) {
//            InventoryChangeParam param = new InventoryChangeParam();
//            param.setBatch("2020060100001");
//            param.setBrokenGrade("eww");
//            param.setContainerCode("310");
//            param.setExpirationDate(new Date(1624982400000L));
//            param.setInventoryType("1002");
//            param.setItemCode("10081");
//            param.setManufactureDate(new Date(1622476800000L));
//            param.setOwnerCode("XF");
//            param.setSpecificationCode("10081");
//            param.setWarehouseCode("WH0120");
//            param.setExecuteQty(i + 1);
//            param.setLocationCode("88888");
//            changeList.add(param);
//        }
//        Map<InventoryUniqueParam, Integer> changMap = new HashMap<>(changeList.size());
//        changeList.forEach(e -> {
//             //InventoryUniqueParam inventoryUniqueParam = e;
//
//            //InventoryUniqueParam inventoryUniqueParam = new InventoryChangeParam();
//            //BeanUtils.copyProperties(e, inventoryUniqueParam);
//
//             InventoryUniqueParam inventoryUniqueParam = new InventoryChangeParam();
//             inventoryUniqueParam.setBatch(e.getBatch());
//             inventoryUniqueParam.setLocationCode(e.getLocationCode());
//             inventoryUniqueParam.setBrokenGrade(e.getBrokenGrade());
//             inventoryUniqueParam.setContainerCode(e.getContainerCode());
//             inventoryUniqueParam.setExpirationDate(e.getExpirationDate());
//             inventoryUniqueParam.setInventoryType(e.getInventoryType());
//             inventoryUniqueParam.setItemCode(e.getItemCode());
//             inventoryUniqueParam.setManufactureDate(e.getManufactureDate());
//             inventoryUniqueParam.setOwnerCode(e.getOwnerCode());
//             inventoryUniqueParam.setSpecificationCode(e.getSpecificationCode());
//             inventoryUniqueParam.setWarehouseCode(e.getWarehouseCode());
//            int qty = e.getExecuteQty();
//            if (changMap.containsKey(inventoryUniqueParam)) {
//                qty = qty + changMap.get(inventoryUniqueParam);
//            }
//            changMap.put(inventoryUniqueParam, qty);
//        });
//        System.out.println(JSON.toJSONString(changeList));
//        System.out.println(JSON.toJSONString(changMap));
//        System.out.println(changeList.size());
//        System.out.println(changMap.size());
//    }
//}
