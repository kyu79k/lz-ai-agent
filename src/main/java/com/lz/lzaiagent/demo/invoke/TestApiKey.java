package com.lz.lzaiagent.demo.invoke;

/**
 * 仅用于测试获取 API Key
 */
public interface TestApiKey {

    // 从环境变量读取 API Key
    String API_KEY = System.getenv("DASHSCOPE_API_KEY") != null ? System.getenv("DASHSCOPE_API_KEY") : "YOUR_DASHSCOPE_API_KEY";
}
