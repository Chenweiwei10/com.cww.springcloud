package com.cww.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cww.springcloud.entities.CommonResult;
import com.cww.springcloud.entities.Payment;
import com.cww.springcloud.handler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource",blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流OK", new Payment(2020L, "serial0001"));
    }

    @GetMapping(value = "/customerBlockHandler")
    @SentinelResource(value = "byResource", blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException2")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "按自定义资源名称限流OK", new Payment(2020L, "serial0001"));
    }


}
