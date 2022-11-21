// package com.sparta.jwtboard2.redis;
//
// import java.time.Duration;
// import java.util.HashMap;
// import java.util.Map;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cache.CacheManager;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.cache.RedisCacheManager;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializationContext;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @RequiredArgsConstructor
// public class CacheConfig {
//
// 	private final RedisConnectionFactory connectionFactory;
//
// 	@Bean
// 	public RedisCacheManager redisCacheManager() {
//
// 		return RedisCacheManager
// 			.RedisCacheManagerBuilder
// 			.fromConnectionFactory(connectionFactory)
// 			.cacheDefaults(defaultConfiguration())
// 			.withInitialCacheConfigurations(customConfigurationMap())
// 			.build();
// 	}
//
// 	private RedisCacheConfiguration defaultConfiguration() {
// 		return RedisCacheConfiguration.defaultCacheConfig()
// 			.serializeKeysWith(fromSerializer(new StringRedisSerializer()))
// 			.serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()))
// 			.entryTtl(Duration.ofMinutes(1));
// 	}
//
// 	private Map<String, RedisCacheConfiguration> customConfigurationMap() {
// 		Map<String, RedisCacheConfiguration> customConfigurationMap = new HashMap<>();
// 		customConfigurationMap.put(RedisCacheKey.NOTICE, defaultConfiguration().entryTtl(Duration.ofSeconds(5L)));
// 		customConfigurationMap.put(RedisCacheKey.VIEWS, defaultConfiguration().entryTtl(Duration.ofDays(1L)));
// 		return customConfigurationMap;
// 	}
// }
