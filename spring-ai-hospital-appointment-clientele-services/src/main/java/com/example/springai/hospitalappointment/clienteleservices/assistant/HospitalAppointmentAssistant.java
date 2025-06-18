package com.example.springai.hospitalappointment.clienteleservices.assistant;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cloud.ai.memory.elasticsearch.ElasticsearchChatMemoryRepository;
import com.example.springai.hospitalappointment.clienteleservices.tools.HospitalAppointmentTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Component
public class HospitalAppointmentAssistant {
    private final ChatClient chatClient;
    @Resource
    private HospitalAppointmentTools hospitalAppointmentTools;

    public HospitalAppointmentAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore,
                                        ElasticsearchChatMemoryRepository elasticsearchChatMemoryRepository) {

        // 配置RAG增强处理器
        Advisor retrievalAdvisor = RetrievalAugmentationAdvisor.builder()
                // 配置查询增强器 允许空上下文查询
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(true)
                        .build())
                // 配置文档检索器
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .build())
                .build();

        // 保存20轮对话
        final int MAX_MESSAGES = 20;
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(elasticsearchChatMemoryRepository)
                .maxMessages(MAX_MESSAGES)
                .build();

        this.chatClient = modelBuilder
                .defaultSystem("你的名字是“Bingo”，你是一家名为“北京协和医院”的智能客服。\n" +
                        "你是一个训练有素的医疗顾问和医疗伴诊助手。\n" +
                        "你态度友好、礼貌且言辞简洁。\n" +
                        "1、请仅在用户发起第一次会话时，和用户打个招呼，并介绍你是谁。\n" +
                        "2、作为一个训练有素的医疗顾问：\n" +
                        "请基于当前临床实践和研究，针对患者提出的特定健康问题，提供详细、准确且实用的医疗建议。请同时考虑可能的病因、诊断流程、治疗方案以及预防措施，并给出在不同情境下的应对策略。对于药物治疗，请特别指明适用的药品名称、剂量和疗程。如果需要进一步的检查或就医，也请明确指示。\n" +
                        "3、作为医疗伴诊助手，你可以回答用户就医流程中的相关问题，主要包含以下功能：\n" +
                        "AI分导诊：根据患者的病情和就医需求，智能推荐最合适的科室。\n" +
                        "AI挂号助手：实现智能查询是否有挂号号源服务；实现智能预约挂号服务；实现智能取消挂号服务。\n" +
                        "4、你必须遵守的规则如下：\n" +
                        "在获取挂号预约详情或取消挂号预约之前，你必须确保自己知晓用户的姓名（必选）、身份证号（必选）、预约科室（必选）、预约日期（必选，格式举例：2025-04-14）、预约时间（必选，格式：上午 或 下午）、预约医生（必选，如果用户没有指定医生，则由系统推荐）。\n" +
                        "当被问到其他领域的咨询时，要表示歉意并说明你无法在这方面提供帮助。\n" +
                        "5、今天是{current_date}。")
                // 插件组合
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(messageWindowChatMemory)
                                .build(),
                        retrievalAdvisor,
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    public String chat(Long chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", DateUtil.today()))
                .user(userMessageContent)
                .advisors(
                        a -> a.param(CONVERSATION_ID, chatId)
                )
                .tools(hospitalAppointmentTools)
                .call().content();
    }

    public Flux<String> chatStream(Long chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", DateUtil.today()))
                .user(userMessageContent)
                .advisors(
                        a -> a.param(CONVERSATION_ID, chatId)
                )
                .tools(hospitalAppointmentTools)
                .stream().content();
    }
}
