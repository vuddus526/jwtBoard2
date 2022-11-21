package com.sparta.jwtboard2.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	// @Bean //요친구가 실제로 Template 역할 Key Serializer, Value Serializer를 통해서 실제 데이터를 변환하는 과정이 필요하다.
	// public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
	// 	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	// 	redisTemplate.setKeySerializer(new StringRedisSerializer());
	// 	redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); //이친구 덕에 객체가 json형태로 변환되는 것으로 생각된다.
	// 	redisTemplate.setConnectionFactory(redisConnectionFactory);
	// 	return redisTemplate;
	// }
	//
	// @Bean //이친구는 Connect와 관련된 객체이다. 보면 HostName Port등을 지정하고 Lettuce까지 지정.
	// public RedisConnectionFactory redisConnectionFactory(){
	// 	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	// 	redisStandaloneConfiguration.setHostName(host);
	// 	redisStandaloneConfiguration.setPort(port);
	// 	LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
	// 	return connectionFactory;
	// }

	// @Bean
	// public RedisConnectionFactory redisConnectionFactory() {
	// 	return new LettuceConnectionFactory(host, port);
	// }

	// @Bean
	// public CacheManager testCacheManager(RedisConnectionFactory cf) {
	// 	RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
	// 		.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
	// 		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
	// 		.entryTtl(Duration.ofMinutes(3L));
	//
	// 	return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf).cacheDefaults(redisCacheConfiguration).build();
	// }


	// @Bean
	// public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
	// 	GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
	// 	// Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(ItemDto.class);
	// 	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	// 	redisTemplate.setConnectionFactory(redisConnectionFactory);
	// 	redisTemplate.setKeySerializer(new StringRedisSerializer());
	// 	// redisTemplate.setValueSerializer(new StringRedisSerializer());
	// 	// redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	// 	redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
	// 	return redisTemplate;
	// }

	@SuppressWarnings("deprecation")
	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer 변경
			.prefixKeysWith("Test:") // Key Prefix로 "Test:"를 앞에 붙여 저장
			.entryTtl(Duration.ofMinutes(30)); // 캐시 수명 30분
		builder.cacheDefaults(configuration);
		return builder.build();
	}

}
