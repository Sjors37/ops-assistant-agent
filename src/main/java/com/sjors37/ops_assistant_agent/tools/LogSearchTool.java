package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.LogEntry;
import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;
import com.sjors37.ops_assistant_agent.repository.LogRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
        boolean hasSeverityFilter = severity != null && !severity.isBlank();

        if (!hasSeverityFilter) {
            return formatResults(logRepository.findByServerName(serverName), serverName, null);
        }

        return parseSeverity(severity)
                .map(parsed -> formatResults(logRepository.findByServerNameAndSeverity(serverName, parsed), serverName, severity))
                .orElse("Invalid severity '" + severity + "'. Valid values are: INFO, WARN, ERROR.");
    }

    private String formatResults(List<LogEntry> results, String serverName, String severityFilter) {
        if (results.isEmpty()) {
            return "No logs found for server '" + serverName + "'"
                    + (severityFilter != null ? " with severity " + severityFilter : "") + ".";
        }

        return results.stream()
                .map(log -> "[%s] %s: %s".formatted(log.timestamp(), log.severity(), log.message()))
                .collect(Collectors.joining("\n"));
    }

    private Optional<LogSeverity> parseSeverity(String severity) {
        try {
            return Optional.of(LogSeverity.valueOf(severity.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}