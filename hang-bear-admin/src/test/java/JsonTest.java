import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/08/22 11:16
 */
@Slf4j
public class JsonTest {
    public static void main(String[] args) {
        //String arrayJsonStr = "[]";
        //List<String> strings = new ArrayList<>();
        //try {
        //    strings = JSON.parseArray(arrayJsonStr, String.class);
        //} catch (Exception e) {
        //    log.error(e.getMessage());
        //    log.error("", e);
        //}
        //log.info("json 数组：{}", strings);
        //
        //// yyMMddHHmm
        //String dateStr = "2108261406";
        //String batch = "2108261406";
        //String batchDateStr = "20" + batch.substring(0, 2) + "-" + batch.substring(2, 4) + "-" + batch.substring(4, 6);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        //SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        //Date parse = null;
        //try {
        //    parse = sdf.parse(dateStr);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //
        //String format = sdf2.format(parse);
        //System.out.println(format);
        //System.out.println(batchDateStr);

        StringBuilder moveTaskCodeBuilder = new StringBuilder();
        moveTaskCodeBuilder.append("2108261406,2108261406,2108261406");
        System.out.println(moveTaskCodeBuilder);
        moveTaskCodeBuilder.deleteCharAt(0);
        System.out.println(moveTaskCodeBuilder);
        moveTaskCodeBuilder.deleteCharAt(moveTaskCodeBuilder.length() - 1);
        System.out.println(moveTaskCodeBuilder);
        if( moveTaskCodeBuilder.length()>12){
            moveTaskCodeBuilder.delete(12,moveTaskCodeBuilder.length());
            System.out.println(moveTaskCodeBuilder);
        }
        String moveTaskCode = "3333,";
        moveTaskCode = moveTaskCode.substring(0, moveTaskCode.length() - 1);
        System.out.println(moveTaskCode);

        Set<String> stringSet = new HashSet<>();
        stringSet.add("01011");
        stringSet.add("10120");
        String s = stringSet.toString();
        String replace = s.replace("]", ",...]");
        System.out.println(s);
        System.out.println(replace);

    }
}
