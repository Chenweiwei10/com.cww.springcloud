package com.cww.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA() {
        return "testA OK";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        return "testA OK";
    }


    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "==========test Hot Key";
    }

    public String dealHotKey(String p1, String p2, BlockException blockException) {
        //系统默认提示：blocked by sentinel (flow limit);自定义限流方案
        return "=================deal hot key";
    }

}
