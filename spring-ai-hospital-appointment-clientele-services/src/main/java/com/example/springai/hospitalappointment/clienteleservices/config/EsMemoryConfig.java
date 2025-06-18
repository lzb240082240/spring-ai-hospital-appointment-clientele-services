package com.example.springai.hospitalappointment.clienteleservices.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.alibaba.cloud.ai.memory.elasticsearch.ElasticsearchChatMemoryRepository;
import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ElasticsearchClient.class)
public class EsMemoryConfig {

    @Bean
    public ElasticsearchChatMemoryRepository elasticsearchChatMemoryRepository(ElasticsearchClient client) {
        return new ElasticsearchChatMemoryRepository(client);
    }
}
