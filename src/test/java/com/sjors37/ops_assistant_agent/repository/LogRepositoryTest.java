package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.LogEntry;
import com.sjors37.ops_assistant_agent.model.enums.LogSeverity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LogRepositoryTest {

    private LogRepository logRepository;

    @BeforeEach
    void setUp() {
        logRepository = new LogRepository();
    }

    @Test
    void findByServerName_returnsAllLogsForServer() {
        List<LogEntry> result = logRepository.findByServerName("web-02");

        assertThat(result).hasSize(3);
        assertThat(result).allMatch(log -> log.serverName().equalsIgnoreCase("web-02"));
    }

    @Test
    void findByServerName_isCaseInsensitive() {
        List<LogEntry> result = logRepository.findByServerName("WEB-02");

        assertThat(result).hasSize(3);
    }

    @Test
    void findByServerName_returnsEmptyList_whenServerHasNoLogs() {
        List<LogEntry> result = logRepository.findByServerName("nonexistent-server");

        assertThat(result).isEmpty();
    }

    @Test
    void findByServerNameAndSeverity_filtersCorrectly() {
        List<LogEntry> result = logRepository.findByServerNameAndSeverity("web-02", LogSeverity.ERROR);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).message()).isEqualTo("Connection pool exhausted");
    }

    @Test
    void findByServerNameAndSeverity_returnsEmptyList_whenNoMatchingSeverity() {
        List<LogEntry> result = logRepository.findByServerNameAndSeverity("staging-01", LogSeverity.ERROR);

        assertThat(result).isEmpty();
    }
}