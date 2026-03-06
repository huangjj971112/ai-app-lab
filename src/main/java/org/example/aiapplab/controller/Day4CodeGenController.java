package org.example.aiapplab.controller;

import org.example.aiapplab.service.OllamaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/day4")
@CrossOrigin
public class Day4CodeGenController {

    private final OllamaService ollamaService;

    public Day4CodeGenController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping(value="/generate-entity", produces="text/event-stream")
    public SseEmitter generateEntity(@RequestParam String tableDesc){

        SseEmitter emitter = new SseEmitter(0L);

        new Thread(() -> {

            try{

                String prompt = """
你是一个Java开发专家。

根据表结构描述生成一个Java实体类。

要求：
1 使用Lombok @Data
2 不要生成getter setter
3 字段添加注释
4 类名使用驼峰
5 返回完整Java代码

表结构：
""" + tableDesc;

                ollamaService.streamChat(prompt, text -> {

                    try{
                        emitter.send(text);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }

                });

                emitter.complete();

            }catch (Exception e){

                emitter.completeWithError(e);

            }

        }).start();

        return emitter;

    }
}