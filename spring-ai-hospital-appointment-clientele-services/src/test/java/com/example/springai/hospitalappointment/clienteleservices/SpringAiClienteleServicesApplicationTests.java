package com.example.springai.hospitalappointment.clienteleservices;

import com.alibaba.cloud.ai.memory.elasticsearch.ElasticsearchChatMemoryRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringAiClienteleServicesApplicationTests {
    @Resource
    private VectorStore vectorStore;

    @Test
    public void contextLoads() throws IOException {
//        // 使用Spring的ResourcePatternResolver来查找所有匹配的资源
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        org.springframework.core.io.Resource[] resources = resolver.getResources("classpath:rag/*");
//
//        for (org.springframework.core.io.Resource resource : resources) {
//            DocumentReader reader = new TextReader(resource);
//            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(
//                    512,
//                    50,
//                    5,
//                    1000,
//                    true);
//
//            List<Document> splitDocuments = tokenTextSplitter.transform(reader.read());
//            vectorStore.write(splitDocuments);
//        }

        List<Document> documents = List.of(
                new Document("AI换模特：这个功能是提供一张模特图然后可以给模特换脸等操作",Map.of("function", "模特")),
                new Document("商品合成：这个功能是提供一张商品图然后可以给商品做合成等操作",Map.of("function", "商品")));
        vectorStore.write(documents);
        System.out.println("处理所有文档结束");
    }

}
