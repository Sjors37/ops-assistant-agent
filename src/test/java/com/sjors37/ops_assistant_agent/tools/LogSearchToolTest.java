package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.LogEntry;
import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;
import com.sjors37.ops_assistant_agent.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogSearchToolTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogSearchTool logSearchTool;

    @Test
    void searchLogs_returnsAllLogs_whenNoSeverityGiven() {
        when(logRepository.findByServerName("web-02")).thenReturn(List.of(
                new LogEntry("web-02", LogSeverity.WARN, "Memory usage above 85%", Instant.now())
        ));

        String result = logSearchTool.searchLogs("web-02", null);

        assertThat(result).contains("WARN").contains("Memory usage above 85%");
    }

    @Test
    void searchLogs_filtersOnSeverity_whenGiven() {
        when(logRepository.findByServerNameAndSeverity("web-02", LogSeverity.ERROR)).thenReturn(List.of(
                new LogEntry("web-02", LogSeverity.ERROR, "Connection pool exhausted", Instant.now())
        ));

        String result = logSearchTool.searchLogs("web-02", "ERROR");

        assertThat(result).contains("Connection pool exhausted");
    }

    @Test
    void searchLogs_isCaseInsensitiveForSeverity() {
        when(logRepository.findByServerNameAndSeverity("web-02", LogSeverity.ERROR)).thenReturn(List.of(
                new LogEntry("web-02", LogSeverity.ERROR, "Connection pool exhausted", Instant.now())
        ));

        String result = logSearchTool.searchLogs("web-02", "error");

        assertThat(result).contains("Connection pool exhausted");
    }

    @Test
    void searchLogs_returnsHelpfulMessage_whenNoLogsFound() {
        when(logRepository.findByServerName("staging-01")).thenReturn(List.of());

        String result = logSearchTool.searchLogs("staging-01", null);

        assertThat(result).contains("No logs found for server 'staging-01'");
    }

    @Test
    void searchLogs_returnsErrorMessage_whenSeverityIsInvalid() {
        String result = logSearchTool.searchLogs("web-02", "CRITICAL");

        assertThat(result).contains("Invalid severity 'CRITICAL'");
    }
}