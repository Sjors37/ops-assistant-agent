package com.sjors37.ops_assistant_agent.controller;

import com.sjors37.ops_assistant_agent.dto.ChatRequest;
import com.sjors37.ops_assistant_agent.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String reply = chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        return new ChatResponse(reply);
    }
}
