package com.cww.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cww.springcloud.entities.CommonResult;
import com.cww.springcloud.entities.Payment;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException blockException) {
        return new CommonResult(444, "按自定义URL限流", new Payment(200L, "serial20"));
    }

    public static CommonResult handlerException2(BlockException blockException) {
        return new CommonResult(444, "按自定义URL限流", new Payment(200L, "serial20"));
    }


}
