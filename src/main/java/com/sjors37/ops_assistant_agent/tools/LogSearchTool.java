package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.LogEntry;
import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;
import com.sjors37.ops_assistant_agent.repository.LogRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogSearchTool {

    private final LogRepository logRepository;

    public LogSearchTool(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Tool(description = "Search recent logs for a specific server, optionally filtered by severity level. " +
            "Use this to investigate why a server might be DOWN or DEGRADED. " +
            "Severity can be INFO, WARN, or ERROR. Leave severity empty to get all logs for the server.")
    public String searchLogs(
            @ToolParam(description = "The name of the server to search logs for, e.g. 'web-02'") String serverName,
            @ToolParam(description = "Optional severity filter: INFO, WARN, or ERROR. Omit to get all severities.", required = false) String severity
    ) {
        List<LogEntry> results;

        if (severity == null || severity.isBlank()) {
            results = logRepository.findByServerName(serverName);
        } else {
            LogSeverity parsedSeverity = parseSeverity(severity);
            if (parsedSeverity == null) {
                return "Invalid severity '" + severity + "'. Valid values are: INFO, WARN, ERROR.";
            }
            results = logRepository.findByServerNameAndSeverity(serverName, parsedSeverity);
        }

        if (results.isEmpty()) {
            return "No logs found for server '" + serverName + "'"
                    + (severity != null && !severity.isBlank() ? " with severity " + severity : "") + ".";
        }

        return results.stream()
                .map(log -> "[%s] %s: %s".formatted(log.timestamp(), log.severity(), log.message()))
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }

    private LogSeverity parseSeverity(String severity) {
        try {
            return LogSeverity.valueOf(severity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}