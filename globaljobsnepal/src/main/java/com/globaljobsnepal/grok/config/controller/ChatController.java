package com.globaljobsnepal.grok.config.controller;

import com.globaljobsnepal.grok.config.service.ChatGptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")

public class ChatController {

    private final ChatGptService chatGptService;

    public ChatController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping
    public ResponseEntity<?> chat(@RequestParam String message) {
        Mono<String> map = chatGptService.sendMessage(message)
                .map(response -> response.getChoices().get(0).getMessage().getContent());

        return ResponseEntity.ok().body(map);
    }
}
