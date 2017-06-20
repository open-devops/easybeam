package com.easybeam.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.easybeam.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantGroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantGroup.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantGroup.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantUser.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantUser.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantRole.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantRole.class.getName() + ".policies", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantRole.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantRole.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantPolicy.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantPolicy.class.getName() + ".permissions", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantPolicy.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantPermission.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.TenantPermission.class.getName() + ".policies", jcacheConfiguration);
            cm.createCache(com.easybeam.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.ProjectAuthorityByGroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.easybeam.domain.ProjectAuthorityByUser.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
