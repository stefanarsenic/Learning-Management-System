package org.example.gatewayservice.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8050/auth")
public interface SecurityFeignClient {

    @PostMapping("/validateToken")
    ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token);
}
