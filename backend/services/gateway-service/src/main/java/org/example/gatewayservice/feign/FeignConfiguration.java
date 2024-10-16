package org.example.gatewayservice.feign;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters();
    }
}

