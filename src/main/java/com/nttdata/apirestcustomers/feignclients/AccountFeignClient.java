package com.nttdata.apirestcustomers.feignclients;

import com.nttdata.apirestcustomers.model.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.util.List;

// quitar la url si es que empezamos a usar gateway
@FeignClient(name = "accounts-service", url = "http://localhost:8082", path = "/api/accounts")
public interface AccountFeignClient {

    @GetMapping("/customer/{customerId}")
    public Flux<AccountDto> getAccounts(@PathVariable("customerId") String customerId);
}
