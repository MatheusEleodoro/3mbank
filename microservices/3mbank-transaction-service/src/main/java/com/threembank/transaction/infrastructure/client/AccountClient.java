package com.threembank.transaction.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "account-service", url = "http://localhost:8080/account")
public interface AccountClient {

    @GetMapping("/account/{id}")
    Map<String,Object> getAccount(@PathVariable("id") String accountId,
                                  @RequestHeader("Authorization") String authorizationHeader);
}
