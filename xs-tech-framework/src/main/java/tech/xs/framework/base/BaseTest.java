package tech.xs.framework.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

/**
 * 授权测试基类
 *
 * @author 沈家文
 * @date 2021/3/4 9:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BaseTest {

    @Resource
    protected MockMvc mockMvc;

}
