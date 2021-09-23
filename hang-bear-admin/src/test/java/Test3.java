import com.wzh.home.entity.po.UmsPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/07/06 10:39
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) {
        // String str = "0123456789";
        // String itemCode = str.substring(0, str.length() - 5);
        // System.out.println(itemCode);
        // String itemCode2 = str.substring(0, 5);
        // System.out.println(itemCode2);

        // String s = String.valueOf(null);
        // System.out.println(s);

        // Double youNumber = 0d;
        //// 注释
        // double floor = Math.floor(youNumber);
        // System.out.println(floor);
        // String str2 = String.format("%.2f", youNumber);
        // System.out.println(str2);
        // System.out.println(String.format("%03f", youNumber));
        // System.out.println(String.format("%03d", 2));
        // System.out.println((int)3.7D);
        // System.out.println(getFiveStr(6d));
        Integer a = 2;
        System.out.println(a.intValue());
        System.out.println(getFiveStr(7.3));

        // Integer a =3;
        // Long b = a.longValue();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("【备货移库任务生成】移库任务参数封装");
        List<UmsPermission> list = new ArrayList<>();
        UmsPermission umsPermission1 = new UmsPermission();
        UmsPermission umsPermission2 = new UmsPermission();
        UmsPermission umsPermission3 = new UmsPermission();
        umsPermission1.setPath("1");
        umsPermission2.setPath("2");
        umsPermission3.setPath("3");
        list.add(umsPermission2);
        list.add(umsPermission1);
        list.add(umsPermission2);

        System.out.println(list);
        list.sort(Comparator.comparing(UmsPermission::getPath));
        System.out.println(list);
        list.addAll(list);
        System.out.println(list);

        list = new ArrayList<>();
        log.info("【备货移库任务生成】新增任务：{}条，任务号：{}", list.size(),
            list.stream().map(UmsPermission::getPath).collect(Collectors.toList()));
        stopWatch.stop();
        log.info("【备货移库任务生成】移库任务参数封装" + stopWatch.getTotalTimeMillis());
        Set<String> stringSet = new HashSet<>();
        stringSet.add("222");
        stringSet.add("333");
        String format = String.format("%s", stringSet);
        Iterator<String> iterator = stringSet.iterator();
        String next = iterator.next();
        String next2 = iterator.next();
        System.out.println(format);
        System.out.println(next);
        System.out.println(next2);

        String format1 = String.format("[%s]规格码在库数量[%d]，已经扫描[%d]，不能再扫描！", "90909", 9L, 9L);
        System.out.println(format1);

    }

    public static String getFiveStr(Double num) {
        String str = "";
        int three = num.intValue();
        String threeStr = String.format("%03d", three);
        String format = String.format("%.2f", num);
        BigDecimal subtract = new BigDecimal(num.toString()).subtract(new BigDecimal(three));
        System.out.println(num - three);
        System.out.println(subtract);
        System.out.println(String.format("%.2f", subtract));
        System.out.println(String.format("%02d", subtract.multiply(new BigDecimal(100)).intValue()));
        String substring = format.substring(format.indexOf(".") + 1);
        str = threeStr + substring;
        return str;
    }
}
