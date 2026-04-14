package com.lz.lzaiagent.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.stereotype.Component;

/**
 * 查询重写器
 *
 * 作用：在 RAG 检索之前，对用户的原始查询进行重写优化，使其更适合向量检索，
 * 从而提高检索的召回率和准确性。
 *
 * 核心流程：
 * 1. 接收用户的原始查询文本
 * 2. 利用 AI 模型（通过 RewriteQueryTransformer）对查询进行语义重写
 * 3. 返回重写后的查询文本
 *
 * 示例：
 * - 原始查询："婚后关系不太亲密" → 重写后："已婚夫妻亲密关系维护方法 建议"
 * - 重写后的查询包含更多关键词，更容易匹配到相关文档
 *
 * 使用场景：在 doChatWithRag 方法中，先调用 queryRewriter.doQueryRewrite() 重写查询，
 * 再用重写后的查询进行 RAG 检索。
 */
@Component
public class QueryRewriter {

    private final QueryTransformer queryTransformer;

    public QueryRewriter(ChatModel dashscopeChatModel) {
        ChatClient.Builder builder = ChatClient.builder(dashscopeChatModel);
        queryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(builder)
                .build();
    }

    /**
     * 执行查询重写
     *
     * @param prompt 用户的原始查询文本
     * @return 重写后的查询文本
     */
    public String doQueryRewrite(String prompt) {
        Query query = new Query(prompt);
        // 执行查询重写
        Query transformedQuery = queryTransformer.transform(query);
        // 输出重写后的查询
        return transformedQuery.text();
    }
}