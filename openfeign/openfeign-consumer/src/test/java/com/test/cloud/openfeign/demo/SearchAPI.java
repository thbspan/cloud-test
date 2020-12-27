package com.test.cloud.openfeign.demo;

import feign.Param;
import feign.RequestLine;

public interface SearchAPI {

    @RequestLine("GET /s?wd={wd}")
    String search(@Param("wd") String wd);
}
