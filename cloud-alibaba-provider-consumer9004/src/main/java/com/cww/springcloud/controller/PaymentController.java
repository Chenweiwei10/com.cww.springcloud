package com.cww.springcloud.controller;


import com.cww.springcloud.entities.CommonResult;
import com.cww.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();

    static {
        hashMap.put(1L,new Payment(1L,"1asfafafafafaf"));
        hashMap.put(1L,new Payment(2L,"2asfafafafafaf"));
        hashMap.put(1L,new Payment(3L,"3asfafafafafaf"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id")Long id){
        Payment payment =hashMap.get(id);
        CommonResult<Payment>  result = new CommonResult(200,"from mysql ,serverPort:"+serverPort,payment);
        return result;
    }

}
