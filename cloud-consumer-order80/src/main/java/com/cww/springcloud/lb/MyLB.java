package com.cww.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getIncrement(){
        int current;
        int next;
        do {
            current =this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0:current+1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getIncrement()%serviceInstances.size();
        return serviceInstances.get(index);
    }


}
