import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/08/07 15:45
 */
public class Sql {
    public static void main(String[] args) {
        //for (int i = 0; i < 256; i++) {
        //    System.out.println("ALTER TABLE t_task_header_" + String.format("%03d", i)
        //        + " ADD `zone_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货区编码';");
        //}

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String dateNow = sdf.format(new Date());
        System.out.println(dateNow);
        sdf = new SimpleDateFormat("yyyyMMddHHmm");
         dateNow = sdf.format(new Date());
        System.out.println(dateNow);
    }
}
