package com.lz.lzaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 恋爱应用向量存储配置
 *
 * 作用：创建并初始化向量存储（VectorStore），将知识库文档转换为向量并存储，
 * 供 RAG 检索使用。
 *
 * 核心流程：
 * 1. 使用 EmbeddingModel（嵌入模型）创建 SimpleVectorStore（基于内存的向量存储）
 * 2. 通过 LoveAppDocumentLoader 加载 Markdown 文档
 * 3. 将文档添加到向量存储中，自动进行向量化处理
 *
 * 注意事项：
 * - SimpleVectorStore 是基于内存的向量存储，应用重启后数据会丢失
 * - 如需持久化，可替换为 PgVectorStore 或其他持久化向量存储实现
 * - EmbeddingModel 由 Spring AI Alibaba 自动配置提供
 *
 * 使用场景：作为 Spring 配置类，在应用启动时自动初始化向量存储 Bean，
 * 供 LoveApp 中的 RAG 相关方法使用。
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;
    
    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();
        // 加载文档
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
