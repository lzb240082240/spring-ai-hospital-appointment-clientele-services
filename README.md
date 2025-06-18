# 基于 Spring AI Alibaba 医院预约客服系统

## 项目目录结构

```
spring-ai-hospital-appointment-clientele-services/
├── src/main/java/com/example/springai/hospitalappointment/clienteleservices/
│   ├── controller/                     # 控制器层
│   │   └── ChatController.java         # 聊天接口控制器，提供同步和流式聊天API
│   ├── assistant/                      # AI助手层  
│   │   └── HospitalAppointmentAssistant.java # 医院预约智能助手，集成RAG和记忆功能
│   ├── tools/                          # 工具函数层
│   │   └── HospitalAppointmentTools.java    # 医院预约工具集，包含预约、取消、查询号源功能
│   ├── entity/                         # 实体模型层
│   │   ├── HospitalAppointment.java    # 医院预约数据模型
│   │   └── UserChat.java              # 用户聊天数据模型
│   ├── service/                        # 业务服务层
│   │   ├── HospitalAppointmentService.java       # 预约服务接口
│   │   └── impl/
│   │       └── HospitalAppointmentServiceImpl.java # 预约服务实现
│   ├── mapper/                         # 数据访问层
│   │   └── HospitalAppointmentMapper.java    # MyBatis映射器
│   ├── config/                         # 配置类层
│   │   ├── EsMemoryConfig.java         # Elasticsearch记忆配置
│   │   ├── ObservationConfig.java      # 观测配置
│   │   └── ToolCallAutoConfig.java     # 工具调用自动配置
│   └── SpringAiClienteleServicesApplication.java # 主启动类
├── src/main/resources/
│   ├── application.properties          # 系统配置文件
│   ├── db/                            # 数据库脚本
│   │   └── hospital_appointment.sql   # 医院预约表结构
│   └── rag/                           # RAG知识库文档
│       ├── hospital.md                # 医院基本信息
│       ├── department.md              # 科室信息
│       ├── neurology.md               # 神经科专业知识
│       └── stomatology.md             # 口腔科专业知识
└── src/test/                          # 测试代码（跑RAG数据到向量库中）
```

## 系统主要功能

这是一个基于**Spring AI**构建的**智能医院预约客服系统**，主要功能包括：

### 🤖 智能对话服务
- **AI聊天助手**: 基于阿里云DashScope（通义千问）的智能客服"Bingo"
- **流式对话**: 支持实时流式响应，提升用户体验
- **多轮对话记忆**: 基于Elasticsearch的对话历史记忆，支持20轮对话上下文

### 🏥 医疗服务功能
- **AI分导诊**: 根据患者病情智能推荐合适科室
- **智能预约挂号**: 自动化医院挂号预约服务
- **号源查询**: 实时查询医院各科室号源情况
- **预约管理**: 支持预约挂号和取消预约功能

### 📚 知识增强(RAG)
- **医疗知识库**: 集成医院信息、科室介绍、专科知识
- **上下文增强**: 基于向量数据库的知识检索增强生成
- **专业医疗咨询**: 提供基于知识库的专业医疗建议

### 🛠️ 技术特性
- **Function Calling**: 智能工具调用，自动执行预约操作
- **向量存储**: 基于Elasticsearch的向量数据库
- **记忆持久化**: Elasticsearch存储对话历史
- **观测监控**: 集成observability监控功能
- **数据持久化**: MySQL数据库存储预约信息

## 技术栈

### 主要版本：
- **JDK21**
- **SpringBoot3.4.5**
- **SpringAI 1.0.0**
- **SpringAI Alibaba 1.0.0.2**

### 系统架构特点
- **分层架构**: 清晰的Controller-Service-Mapper分层
- **插件化设计**: 可扩展的Advisor和Tool机制
- **配置化管理**: 统一的配置文件管理
- **观测能力**: 完整的监控和日志体系
