//import com.wzh.home.entity.po.UmsPermission;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * <p>
// *
// * </p>
// *
// * @author weizhuohang
// * @since 2021/5/21 9:16
// */
//public class Test {
//
//    public static void main(String[] args) {
//        // String format = String.format("第%d行缺少备货库位1", 2);
//        // System.out.println(format);
//        //
//        // List<UmsPermission> permissionList = new ArrayList<>();
//        // UmsPermission umsPermission = new UmsPermission();
//        // umsPermission.setPath("/wzh0");
//        // permissionList.add(umsPermission);
//        // System.out.println(permissionList.get(0).getPath());
//        // umsPermission.setPath("/wzh1");
//        // System.out.println(permissionList.get(0).getPath());
//        // UmsPermission umsPermission2 = umsPermission;
//        // umsPermission2.setPath("/wzh2");
//        // System.out.println(permissionList.get(0).getPath());
//        // UmsPermission umsPermission3 = new UmsPermission();
//        // BeanUtils.copyProperties(umsPermission, umsPermission3);
//        // umsPermission3.setPath("/wzh3");
//        // System.out.println(permissionList.get(0).getPath());
//        // UmsPermission umsPermission4 = umsPermission;
//        // BeanUtils.copyProperties(umsPermission3, umsPermission4);
//        // umsPermission4.setPath("/wzh4");
//        // System.out.println(permissionList.get(0).getPath());
//        //
//        // List<String> strings = new ArrayList<>();
//        // strings.add("a");
//        // strings.add("b");
//        // strings.add("c");
//        // strings.add("a");
//        // List<String> strings2 = new ArrayList<>();
//        // strings2.add("a");
//        // strings2.add("b");
//        // strings2.add("f");
//        // boolean b = strings.containsAll(strings2);
//        //// strings.contains();
//        // System.out.println(b);
//        //
//        // permissionList = new ArrayList<>();
//        // for (int i = 0; i < 6; i++) {
//        // UmsPermission permission = new UmsPermission();
//        // int num = i % 3;
//        // permission.setPath("wzh" + num);
//        // permissionList.add(permission);
//        // }
//        // List<String> collect = permissionList.stream().map(UmsPermission::getPath).collect(Collectors.toList());
//        // System.out.println(collect);
//        // List<String> distinct = permissionList.stream().map(UmsPermission::getPath).collect(Collectors.toList())
//        // .stream().distinct().collect(Collectors.toList());
//        // System.out.println(distinct);
//        // UmsPermission permission = new UmsPermission();
//        // permissionList.add(permission);
//        // List<String> distinct2 =
//        // permissionList.stream().map(UmsPermission::getPath).distinct().collect(Collectors.toList());
//        //
//        // System.out.println(distinct2);
//        // distinct2 = distinct2.subList(0, 1);
//        // System.out.println(distinct2);
//        //
//        // List<Integer> integers = Arrays.asList(1, 2);
//        // System.out.println(integers);
//        //
//        // System.out.println(permissionList);
//        // permissionList = new ArrayList<>();
//        // int sum = permissionList.stream().mapToInt(UmsPermission::getNum).sum();
//        // System.out.println(sum);
//        //
//
//        Integer integer = null;
//        if (0 >= integer) {
//            System.out.println(0);
//        }
//
//        List<UmsPermission> permissionList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            UmsPermission permission = new UmsPermission();
//            int num = i % 3;
//            permission.setPath("wzh" + num);
//            int two = i % 2;
//            if (two == 0) {
//                permission.setQty(new BigDecimal(8));
//            } else {
//                permission.setQty(new BigDecimal(-8));
//            }
//            permissionList.add(permission);
//            System.out.println(permission.getQty() + "......." + permission.getQty().compareTo(BigDecimal.ZERO));
//        }
//        System.out.println(permissionList);
//        List<UmsPermission> wzh0 =
//            permissionList.stream().filter(e -> e.getPath().equals("wz66")).collect(Collectors.toList());
//        System.out.println("wzh0" + wzh0);
//        int sumQty = wzh0.stream().mapToInt(UmsPermission::getNum).sum();
//        System.out.println(sumQty);
//        List<UmsPermission> wzh9 =
//            permissionList.stream().filter(e -> e.getQty().compareTo(BigDecimal.ZERO) < 0).collect(Collectors.toList());
//        System.out.println(wzh9);
//        List<UmsPermission> wzh10 =
//            wzh9.stream().filter(e -> e.getQty().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
//        System.out.println(wzh10);
//
//        Map<String, String> itemMap = new HashMap<>();
//        itemMap.put("70087", "70087");
//        Map<String, String> itemMap2 = new HashMap<>();
//        itemMap2.put("70087", "70087");
//        Set<String> itemSet = new HashSet<>();
//        itemSet = itemMap2.keySet();
//        itemSet = new HashSet<>(itemMap2.keySet());
//        // itemSet.add("70087");
//        itemSet.addAll(itemMap.keySet());
//        System.out.println(itemSet);
//    }
//}
