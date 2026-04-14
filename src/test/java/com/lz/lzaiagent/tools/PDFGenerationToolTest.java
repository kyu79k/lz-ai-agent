package com.lz.lzaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "哔哩哔哩 (゜-゜)つロ 干杯~.pdf";
        String content = "哔哩哔哩 (゜-゜)つロ 干杯~ https://www.bilibili.com/";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
