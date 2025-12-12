package com.maxim.lab1.db.aop;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CacheLoggingAspect {

    CacheStatisticsProvider cacheStatisticsProvider;

    CacheLoggingProperties cacheLoggingProperties;

    @Around("@annotation(com.maxim.lab1.db.aop.LogCacheStatistics)")
    public Object logCacheStatistics(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            var annotation = ((MethodSignature) joinPoint.getSignature())
                    .getMethod().getAnnotation(LogCacheStatistics.class);
            Object result = joinPoint.proceed();
            logStatistics(annotation.level());
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }

    private void logStatistics(LogCacheStatistics.LogLevel level) {
        if (!cacheLoggingProperties.isEnabled()) {
            return;
        }

        var statistics = cacheStatisticsProvider.getStatistics();

        String message = "CacheStatistics" +
                " hits: " + statistics.hits() +
                " misses: " + statistics.misses() +
                " puts: " + statistics.puts();

        resolveLevel(level)
                .log(message);
    }

    private LoggingEventBuilder resolveLevel(LogCacheStatistics.LogLevel level) {
        return switch (level) {
            case INFO -> log.atInfo();
            case WARN -> log.atWarn();
            case ERROR -> log.atError();
            case DEBUG -> log.atDebug();

        };
    }


}
