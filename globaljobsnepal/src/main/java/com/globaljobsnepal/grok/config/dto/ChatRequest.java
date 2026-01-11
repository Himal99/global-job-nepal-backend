package com.globaljobsnepal.grok.config.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;

    // getters and setters

    @Data
    public static class Message {
        private String role; // "user", "system", or "assistant"
        private String content;

        // getters and setters
    }
}