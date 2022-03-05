package tech.xs.auth.test.base;

import org.springframework.boot.test.context.SpringBootTest;
import tech.xs.auth.AuthApplication;
import tech.xs.framework.base.BaseTest;

/**
 * 授权测试基类
 *
 * @author 沈家文
 * @date 2021/3/4 9:44
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AuthApplication.class)
public class AuthBaseTest extends BaseTest {


}
