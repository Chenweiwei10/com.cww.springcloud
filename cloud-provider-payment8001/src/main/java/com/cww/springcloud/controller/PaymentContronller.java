package com.cww.springcloud.controller;


import com.cww.springcloud.entities.CommonResult;
import com.cww.springcloud.entities.Payment;
import com.cww.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentContronller {

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/creat")
    public CommonResult creat(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果："+payment);
       if (result>0){
           return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);
       } else
            return new CommonResult(400,"插入数据库失败,serverPort:"+serverPort,null);
        }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("插入结果："+payment);
        if (null !=payment){
            return new CommonResult(200,"插入数据库成功，serverPort:"+serverPort,payment);
        } else
            return new CommonResult(400,"查询失败，数据库没有数据，serverPort:"+serverPort+"订单ID"+id,null);
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services =discoveryClient.getServices();
        for (String element:services){
            log.info("******element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        /*List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-ORDER-SERVICE");*/
        for (ServiceInstance instance:instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentTimeOut(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch(InterruptedException e){
            log.info(e.getMessage());
        }
        return serverPort;
    }

}
