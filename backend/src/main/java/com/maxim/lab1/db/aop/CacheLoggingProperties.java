package com.maxim.lab1.db.aop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties("app.l2cache")
public class CacheLoggingProperties {

    private boolean enabled;
}
