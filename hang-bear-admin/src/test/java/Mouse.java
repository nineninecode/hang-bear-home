import com.wzh.home.entity.po.UmsPermission;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/15 16:57
 */
public class Mouse {
    public static void main(String[] args) throws AWTException {
        // Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // Integer width = new Double(d.getWidth()).intValue();
        // Integer height = new Double(d.getHeight()).intValue();
        // Robot robot = new Robot();
        // robot.mouseMove(width/2, height/2);
        // robot.delay(1000);
        // robot.mousePress(InputEvent.BUTTON1_MASK);
        // robot.mouseMove(width/2+100, height/2+100);
        // robot.mouseRelease(InputEvent.BUTTON1_MASK);
        // robot.mouseMove(-1920, 880);
        //// robot.keyPress(56);
        //// 获取屏幕分辨率
        // System.out.println(d);

        List<UmsPermission> list = new ArrayList<UmsPermission>();
        UmsPermission moveDoc = new UmsPermission();
        for (int i = 0; i < 4; i++) {
            moveDoc.setPath("index：" + i);
            list.add(moveDoc);
             moveDoc = new UmsPermission();
        }
        System.out.println(Arrays.toString(list.toArray()));
    }
}
