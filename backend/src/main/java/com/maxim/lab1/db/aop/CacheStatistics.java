package com.maxim.lab1.db.aop;

public record CacheStatistics(Long hits, Long misses, Long puts) {
}
