package org.example.aiapplab.controller;

import org.example.aiapplab.service.OllamaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@RestController
@RequestMapping("/day5")
@CrossOrigin
public class Day5CrudGenController {

    private final OllamaService ollamaService;

    public Day5CrudGenController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping(value = "/generate-crud", produces = "text/event-stream")
    public SseEmitter generateCrud(@RequestParam String tableDesc) {

        SseEmitter emitter = new SseEmitter(0L);

        new Thread(() -> {

            try {

                String prompt = """
你是一个资深Java开发专家。

根据 MySQL 表结构 SQL 生成完整 CRUD 代码。

生成：

1 Entity
2 Mapper
3 Service
4 ServiceImpl
5 Controller

输出必须严格按照以下格式：

=== User.java ===
代码

=== UserMapper.java ===
代码

=== UserService.java ===
代码

=== UserServiceImpl.java ===
代码

=== UserController.java ===
代码

SQL如下：
""" + tableDesc;

                ollamaService.streamChat(prompt, text -> {

                    try {
                        emitter.send(text);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });

                emitter.complete();

            } catch (Exception e) {

                emitter.completeWithError(e);

            }

        }).start();

        return emitter;

    }
}