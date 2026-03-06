package org.example.aiapplab.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AIResponseParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static CodeReviewResult parse(String text) {

        try {
            return mapper.readValue(text, CodeReviewResult.class);
        } catch (Exception e) {
            throw new RuntimeException("AI返回JSON解析失败: " + text);
        }

    }

}
