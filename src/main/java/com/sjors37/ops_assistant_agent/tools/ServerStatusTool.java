package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.Server;
import com.sjors37.ops_assistant_agent.repository.ServerRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServerStatusTool {

    private final ServerRepository serverRepository;

    public ServerStatusTool(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Tool(description = "Check the current health status of a server by its name. " +
            "Returns whether the server is UP, DOWN, or DEGRADED, and which environment it runs in.")
    public String checkServerStatus(
            @ToolParam(description = "The name of the server to check, e.g. 'web-01'") String serverName
    ) {
        return serverRepository.findByName(serverName)
                .map(this::formatStatus)
                .orElse("No server found with name '" + serverName + "'. " +
                        "Available servers: " + availableServerNames());
    }

    private String formatStatus(Server server) {
        return "Server '%s' (%s) is currently %s."
                .formatted(server.name(), server.environment(), server.status());
    }

    private String availableServerNames() {
        List<String> names = serverRepository.findAll().stream()
                .map(Server::name)
                .toList();
        return String.join(", ", names);
    }
}
