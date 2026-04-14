package com.lz.lzaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

/**
 * 自定义 RAG 检索增强顾问工厂
 *
 * 作用：根据用户状态（单身/恋爱/已婚）过滤文档，只检索与该状态相关的知识库内容，
 * 从而提供更精准的恋爱建议。
 *
 * 核心流程：
 * 1. 根据传入的 status 参数构建过滤表达式（如 status="单身"）
 * 2. 创建文档检索器，设置相似度阈值和返回文档数量
 * 3. 组装 RetrievalAugmentationAdvisor（检索增强顾问），将检索器和查询增强器结合
 *
 * 使用场景：在 doChatWithRag 方法中，通过此工厂创建自定义的 RAG Advisor，
 * 替代默认的 QuestionAnswerAdvisor，实现按状态过滤的精准检索。
 */
@Slf4j
public class LoveAppRagCustomAdvisorFactory {
    /**
     * 创建自定义 RAG 检索增强顾问
     *
     * @param vectorStore 向量存储（知识库）
     * @param status      用户状态过滤条件（如 "单身"、"恋爱"、"已婚"）
     * @return 组装好的检索增强顾问
     */
    public static Advisor createLoveAppRagCustomAdvisor(VectorStore vectorStore, String status) {
        Filter.Expression expression = new FilterExpressionBuilder()
                .eq("status", status)
                .build();
        //创建文档检索器
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .filterExpression(expression) // 过滤条件
                .similarityThreshold(0.5) // 相似度阈值
                .topK(3) // 返回文档数量
                .build();

        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryAugmenter(LoveAppContextualQueryAugmenterFactory.createInstance())
                .build();
    }
}