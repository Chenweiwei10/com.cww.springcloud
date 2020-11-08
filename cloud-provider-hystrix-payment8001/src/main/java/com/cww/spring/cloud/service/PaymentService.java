package com.cww.spring.cloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@DefaultProperties(defaultFallback="paymentInfo_global_FallbackMethod")
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_OK,id:"+"\t"+id+"\t"+"nice down";
    }

    @HystrixCommand
    public String paymentInfo_TimeOut(Integer id){
        int timeOutNum = 5;
        try{
            TimeUnit.SECONDS.sleep(timeOutNum);
        }catch (InterruptedException e){
            log.info(e.getMessage());
        }
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id:"+"\t"+id+"\t"+"time out";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id:"+"\t"+id+"\t"+"time out ";
    }

    public String paymentInfo_global_FallbackMethod(Integer id){
        return "Global异常，请稍后再试";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
    })
    public String paymentCircuitBreaker(Integer id){
        if (id<0){
            throw new RuntimeException("Id 不能为负数，请稍后再试，id:"+"\t"+id);
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号:"+"\t"+id;
    }

    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能为负数，请稍后再试，id:"+"\t"+id;
    }


}
