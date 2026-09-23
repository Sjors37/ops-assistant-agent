package com.sjors37.ops_assistant_agent.model;

import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;

import java.time.Instant;

public record LogEntry(
        String serverName,
        LogSeverity severity,
        String message,
        Instant timestamp
) {}
