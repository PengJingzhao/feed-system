package com.pjz.feed.filter;

import com.pjz.feed.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private LogService logService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求信息
        ServerHttpRequest request = exchange.getRequest();

        // 控制台打印日志
        logService.consoleLog(request);

        // 文件记录日志
        logService.fileLog(request);

        // 记录写入数据库
        logService.saveLogInDatabase(request);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
