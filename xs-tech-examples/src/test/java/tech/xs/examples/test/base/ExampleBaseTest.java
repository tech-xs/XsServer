package tech.xs.examples.test.base;

import org.springframework.boot.test.context.SpringBootTest;
import tech.xs.examples.ExamplesApplication;
import tech.xs.framework.base.BaseTest;

/**
 * 示例Test基类
 *
 * @author 沈家文
 * @date 2021/3/4 9:57
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ExamplesApplication.class)
public class ExampleBaseTest extends BaseTest {

}
