package com.lz.lzaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 恋爱应用文档加载器
 *
 * 作用：从 classpath 下的 document 目录中加载所有 Markdown 文档，
 * 并将它们转换为 Spring AI 的 Document 对象列表，供向量存储使用。
 *
 * 核心流程：
 * 1. 通过 ResourcePatternResolver 扫描 classpath:document/*.md 下的所有 Markdown 文件
 * 2. 使用 MarkdownDocumentReader 逐个解析 Markdown 文件
 * 3. 为每个文档添加文件名元数据（filename），方便后续按文件名过滤
 * 4. 返回所有解析后的 Document 列表
 *
 * 配置说明：
 * - withHorizontalRuleCreateDocument(true)：水平分割线创建新文档
 * - withIncludeCodeBlock(false)：不包含代码块内容
 * - withIncludeBlockquote(false)：不包含引用块内容
 *
 * 使用场景：被 LoveAppVectorStoreConfig 调用，在应用启动时加载知识库文档到向量存储中。
 */
@Component
@Slf4j
class LoveAppDocumentLoader {

    private final ResourcePatternResolver resourcePatternResolver;

    LoveAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 加载多篇 Markdown 文档
     * @return
     */
    public List<Document> loadMarkdowns() {
        List<Document> allDocuments = new ArrayList<>();
        try {
            // 这里可以修改为你要加载的多个 Markdown 文件的路径模式
            Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", fileName)
                        .build();
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
                allDocuments.addAll(reader.get());
            }
        } catch (IOException e) {
            log.error("Markdown 文档加载失败", e);
        }
        return allDocuments;
    }
}
