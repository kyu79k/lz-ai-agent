package com.lz.lzaiagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * 上下文查询增强器工厂
 *
 * 作用：创建 ContextualQueryAugmenter（上下文查询增强器），用于在 RAG 流程中
 * 将检索到的文档内容附加到用户查询中，使 AI 能够基于知识库内容回答问题。
 *
 * 核心功能：
 * - 当检索到相关文档时，将文档内容作为上下文拼接到用户问题中
 * - 当未检索到相关文档时（allowEmptyContext=false），返回预设的提示信息，
 *   告知用户只能回答恋爱相关问题，避免 AI 产生幻觉
 *
 * 使用场景：被 LoveAppRagCustomAdvisorFactory 调用，作为检索增强顾问的查询增强器组件。
 */
public class LoveAppContextualQueryAugmenterFactory {

    public static ContextualQueryAugmenter createInstance() {
        PromptTemplate emptyContextPromptTemplate = new PromptTemplate("""
                你应该输出下面的内容：
                抱歉，我只能回答恋爱相关的问题，别的没办法帮到您哦，
                有问题可以联系 https://www.bilibili.com/
                """);
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)
                .emptyContextPromptTemplate(emptyContextPromptTemplate)
                .build();
    }
}