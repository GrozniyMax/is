package com.maxim.lab1.db.aop;

import org.apache.logging.log4j.Level;
import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogCacheStatistics {

    LogLevel level() default LogLevel.INFO;

    enum LogLevel {
        INFO,
        WARN,
        ERROR,
        DEBUG,
    }
}
