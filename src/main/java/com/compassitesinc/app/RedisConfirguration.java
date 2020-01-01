package com.compassitesinc.app;

import com.compassitesinc.chat.operator.constructs.ChannelMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by prakashjetty on 12/10/18.
 */
@Configuration
@ComponentScan("com.compassitesinc")

public class RedisConfirguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, ChannelMessage> redisTemplate() {
        RedisTemplate<String, ChannelMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
