package com.sjors37.ops_assistant_agent.config;

import com.sjors37.ops_assistant_agent.tools.ServerStatusTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private static final String SYSTEM_PROMPT = """
            You are an ops assistant that helps engineers check the health of servers,
            search logs, and file tickets when something is wrong.

            Guidelines:
            - Always use the available tools to get real, current information instead
              of guessing or assuming server status.
            - If a server is DOWN or DEGRADED, proactively ask the user if they want
              a ticket filed, but never create one without the user asking or confirming.
            - Keep responses concise and factual. State the server name, environment,
              and status clearly.
            - If a tool returns an error (e.g. server not found), relay that clearly
              to the user along with any suggestions the tool provided.
            """;

    @Bean
    public ChatClient chatClient(AnthropicChatModel chatModel, ServerStatusTool serverStatusTool) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(serverStatusTool)
                .build();
    }
}