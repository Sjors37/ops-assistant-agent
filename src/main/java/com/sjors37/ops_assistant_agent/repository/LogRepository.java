package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.LogEntry;
import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class LogRepository {

    private final List<LogEntry> logs = List.of(
            new LogEntry("web-01", LogSeverity.INFO, "Health check passed", Instant.now().minus(2, ChronoUnit.HOURS)),
            new LogEntry("web-02", LogSeverity.WARN, "Response time exceeded 2000ms", Instant.now().minus(90, ChronoUnit.MINUTES)),
            new LogEntry("web-02", LogSeverity.ERROR, "Connection pool exhausted", Instant.now().minus(45, ChronoUnit.MINUTES)),
            new LogEntry("web-02", LogSeverity.WARN, "Memory usage above 85%", Instant.now().minus(30, ChronoUnit.MINUTES)),
            new LogEntry("db-01", LogSeverity.ERROR, "Failed to establish connection", Instant.now().minus(10, ChronoUnit.MINUTES)),
            new LogEntry("staging-01", LogSeverity.INFO, "Deployment completed successfully", Instant.now().minus(1, ChronoUnit.DAYS))
    );

    public List<LogEntry> findByServerName(String serverName) {
        return logs.stream()
                .filter(log -> log.serverName().equalsIgnoreCase(serverName))
                .toList();
    }

    public List<LogEntry> findByServerNameAndSeverity(String serverName, LogSeverity severity) {
        return logs.stream()
                .filter(log -> log.serverName().equalsIgnoreCase(serverName))
                .filter(log -> log.severity() == severity)
                .toList();
    }
}