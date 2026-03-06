package org.example.aiapplab.controller;

import org.example.aiapplab.service.OllamaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/day3")
public class Day3StreamChatController {

    private final OllamaService ollamaService;

    public Day3StreamChatController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping(value="/stream-chat", produces="text/event-stream")
    public SseEmitter streamChat(@RequestParam String message){

        SseEmitter emitter = new SseEmitter(0L);

        new Thread(() -> {

            try{

                String prompt = "请用简单易懂的方式回答：" + message;

                ollamaService.streamChat(prompt,text -> {

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