package com.pjz.feed.service;

import com.pjz.feed.entity.VisitorsLog;
import com.pjz.feed.mapper.VisitorLogsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class LogService {

    @Resource
    private VisitorLogsMapper visitorLogsMapper;

    @Async
    public void consoleLog(ServerHttpRequest request) {
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "Unknown";
        }

        log.info("== Visitor Request Log ==");
        log.info("Time: {}", LocalDateTime.now());
        log.info("Client IP: {}", clientIp);
        log.info("Method: {}", method);
        log.info("Path: {}", path);
        log.info("=========================");
    }

    @Async
    public void fileLog(ServerHttpRequest request) {
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "Unknown";
        }
    }

    @Async
    public void saveLogInDatabase(ServerHttpRequest request) {
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "Unknown";
        }

        VisitorsLog visitorsLog = new VisitorsLog();
        visitorsLog.setClientIp(clientIp);
        visitorsLog.setMethod(method);
        visitorsLog.setVisitTime(new Timestamp(System.currentTimeMillis()));
        visitorsLog.setPath(path);

        visitorLogsMapper.add(visitorsLog);
    }

}
