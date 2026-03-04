package org.example.aiapplab.controller;
import lombok.RequiredArgsConstructor;
import org.example.aiapplab.prompt.PromptBuilder;
import org.example.aiapplab.prompt.PromptTemplate;
import org.example.aiapplab.service.LlmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final LlmService llmService;

    @PostMapping("/review")
    public String review(@RequestBody String code) {

        PromptTemplate template = new PromptTemplate();
        template.setRole("你是一个资深Java代码审查专家。");
        template.setTask("请分析以下代码问题：\n" + code);
        template.setConstraints(List.of(
                "不允许编造问题",
                "不确定必须说明",
                "必须输出JSON"
        ));
        template.setOutputFormat("""
        {
          "logicIssues": [],
          "performanceIssues": [],
          "securityRisks": [],
          "suggestions": []
        }
        """);
        System.out.println("====== PROMPT ======");
        String prompt = PromptBuilder.build(template);
        System.out.println(prompt);
        System.out.println("====================");

        return llmService.callModel(prompt);
    }
}