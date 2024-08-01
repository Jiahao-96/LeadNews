package com.heima.gateway.filter;

import com.heima.gateway.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
全局过滤器，校验token是否有效
 */
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    /**
     * 执行过滤的方法
     * @param exchange 交换机，可以获取请求和响应对象
     * @param chain 过滤器链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //1. 如果是登录，直接放行
        String path = request.getURI().getPath();
        if (path.contains("/login/login_auth")) {
            return chain.filter(exchange);
        }

        //2. 获取token，如果没有token，不放行
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)) {
            log.warn("请求路径：{}，没有token，不放行", path);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //3. 如果token过期或非法，不放行
        try {
            Claims claims = AppJwtUtil.getClaimsBody(token);
            int i = AppJwtUtil.verifyToken(claims);
            if (i == 1 || i == 2) {
                log.warn("请求路径：{}，没有token，不放行", path);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        } catch (Exception e) {
            log.warn("请求路径：{}，没有token，不放行", path);
            e.printStackTrace();

            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //4. 放行
        return chain.filter(exchange);
    }

    /**
     * 过滤的执行的优先级，返回值越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
