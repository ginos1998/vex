package com.vex.controllers.commons;

import com.vex.exceptions.ServiceException;
import com.vex.utils.JwtParam;
import com.vex.utils.JwtValues;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}")
public class CheckHealthController {
    @GetMapping("/greeting")
    public Mono<String> test(org.springframework.http.server.reactive.ServerHttpRequest request) throws ServiceException {
        String ok = "hi " + JwtValues.getParam(JwtParam.USERNAME, request.getHeaders().getFirst("Authorization")) + "!";
        return Mono.just(ok);
    }

    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("ok");
    }
}
