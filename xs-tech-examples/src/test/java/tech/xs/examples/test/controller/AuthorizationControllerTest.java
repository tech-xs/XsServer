package tech.xs.examples.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import tech.xs.examples.test.base.ExampleBaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
public class AuthorizationControllerTest extends ExampleBaseTest {

    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post("/auth/authorization/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        log.info(mvcResult.toString());
    }

}
