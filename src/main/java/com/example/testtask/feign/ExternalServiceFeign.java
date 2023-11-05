package com.example.testtask.feign;

import com.example.testtask.dto.UserShortDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "external-service-feign", url = "http://external.service")
public interface ExternalServiceFeign {

    @GetMapping("/api/v1/user/{login}")
    UserShortDto readUserByLogin(@PathVariable String login);
}
