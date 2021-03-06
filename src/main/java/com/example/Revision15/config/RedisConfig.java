package com.example.Revision15.config;

//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.Revision15.Constants;
import org.apache.catalina.Manager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
public class RedisConfig{
        private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    
        @Value("${spring.redis.host}") 
        private String redisHost;

        @Value("${spring.redis.port}") 
        private Integer redisPort;

        private String redisPassword;

//        @Bean
//        public RedisCacheManager cacheManager(){
//                RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
//                redisCacheManager.setTransactionAware(true);
//                redisCacheManager.setLoadRemoteCacheOnStartup(true);
//
//        }

        
        @Bean
        @Scope("singleton")
        public RedisTemplate<String, Object> redisTemplate(){
                final RedisStandaloneConfiguration config 
                        = new RedisStandaloneConfiguration();
                redisPassword = System.getenv(Constants.ENV_REDIS_KEY);
                config.setHostName(redisHost);
                config.setPort(redisPort);
                config.setPassword(redisPassword);

                final JedisClientConfiguration jedisClient = JedisClientConfiguration
                                .builder().build();
                final JedisConnectionFactory jedisFac = 
                        new JedisConnectionFactory(config, jedisClient); jedisFac.afterPropertiesSet();
                        
                RedisTemplate<String, Object> template = new RedisTemplate<>();
                template.setConnectionFactory(jedisFac);
//                template.setExposeConnection(true);
                template.setKeySerializer(new StringRedisSerializer()); 
//                RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
                template.setValueSerializer(new StringRedisSerializer());
                return template;
        }
}