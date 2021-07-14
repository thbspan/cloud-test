package com.test.cloud.openfeign.demo;

import org.junit.jupiter.api.Test;

import feign.Feign;

public class SearchAPITest {

    @Test
    public void test() {
        SearchAPI searchAPI = Feign.builder().target(SearchAPI.class, "http://www.baidu.com");
        System.out.println(searchAPI.search("java"));
    }
}
