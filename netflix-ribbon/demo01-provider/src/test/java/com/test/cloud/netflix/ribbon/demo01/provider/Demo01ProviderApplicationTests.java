package com.test.cloud.netflix.ribbon.demo01.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.test.cloud.netflix.ribbon.demo01.provider.controller.DemoController;

@AutoConfigureMockMvc
@SpringBootTest
public class Demo01ProviderApplicationTests {

    @Autowired
    private DemoController demoController;

    @Test
    public void testEcho() throws InterruptedException {
        System.out.println(demoController.echo("test"));
    }

    @Autowired
    private MockMvc mockMvc;

    /**
     * 参考 https://blog.csdn.net/qq_16513911/article/details/83018027
     */
    @Test
    public void testMock() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/echo").param("name", "test");
        ResultActions resultActions = mockMvc.perform(builder);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
