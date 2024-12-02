package com.healthmanagerservice.healthmanagerservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
     @Bean
     public CaffeineCacheManager cacheManager() {
          CaffeineCacheManager cacheManager = new CaffeineCacheManager("brazilianStates");
          cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS));
          return cacheManager;
     }
}