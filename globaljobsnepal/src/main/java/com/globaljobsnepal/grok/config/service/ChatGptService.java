package com.globaljobsnepal.grok.config.service;

import com.globaljobsnepal.grok.config.dto.ChatRequest;
import com.globaljobsnepal.grok.config.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
public class ChatGptService {

    private final WebClient webClient;

    public ChatGptService(@Value("${openai.api.key}") String apiKey,
                          @Value("${openai.api.url}") String apiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Mono<ChatResponse> sendMessage(String userMessage) {
        ChatRequest request = new ChatRequest();
        request.setModel("gpt-3.5-turbo");
        ChatRequest.Message message = new ChatRequest.Message();
        message.setRole("user");
        message.setContent(userMessage);
        request.setMessages(List.of(message));

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.value() == 429, clientResponse ->
                        Mono.error(new RuntimeException("Rate limit exceeded. Please wait and try again."))
                )
                .bodyToMono(ChatResponse.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2)) // retry 3 times with exponential backoff
                                .filter(throwable -> throwable instanceof RuntimeException &&
                                        throwable.getMessage().contains("Rate limit"))
                );
    }
}
