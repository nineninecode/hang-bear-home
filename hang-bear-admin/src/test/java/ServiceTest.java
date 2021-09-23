import com.wzh.home.service.impl.IUmsUserServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/5/27 8:49
 */
public class ServiceTest {

    @Test
    public void test(){
        IUmsUserServiceImpl iUmsUserService = new IUmsUserServiceImpl();
        iUmsUserService.resetUserPassword("wzh");
    }
}
