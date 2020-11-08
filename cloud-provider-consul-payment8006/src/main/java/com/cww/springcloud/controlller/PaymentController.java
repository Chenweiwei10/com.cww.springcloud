package com.cww.springcloud.controlller;


import com.cww.springcloud.entities.CommonResult;
import com.cww.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;


    @RequestMapping(value = "/payment/consul")
    public String paymentConsul(){
        return "spring cloud with consul : "+serverPort+"\t "+ UUID.randomUUID().toString();
    }

    public CommonResult creat(@RequestBody Payment payment){
        return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,payment);
    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        return new CommonResult(200,"查询数据库成功，serverPort:"+serverPort+"查询Id:"+id);
    }

}
