package com.maxim.lab1.db.aop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CacheStatisticsProvider {

    EntityManager entityManager;

    public CacheStatistics getStatistics() {
        var session = entityManager.unwrap(Session.class);
        var statistics = session.getSessionFactory().getStatistics();

        return new CacheStatistics(
                statistics.getSecondLevelCacheHitCount(),
                statistics.getSecondLevelCacheMissCount(),
                statistics.getSecondLevelCachePutCount()
        );
    }
}
