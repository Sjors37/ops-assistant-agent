package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.Server;
import com.sjors37.ops_assistant_agent.model.ServerStatus;
import com.sjors37.ops_assistant_agent.repository.ServerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerStatusToolTest {

    @Mock
    private ServerRepository serverRepository;

    @InjectMocks
    private ServerStatusTool serverStatusTool;

    @Test
    void checkServerStatus_returnsFormattedStatus_whenServerExists() {
        Server server = new Server("web-01", "web-01", ServerStatus.UP, "production");
        when(serverRepository.findByName("web-01")).thenReturn(Optional.of(server));

        String result = serverStatusTool.checkServerStatus("web-01");

        assertThat(result).isEqualTo("Server 'web-01' (production) is currently UP.");
    }

    @Test
    void checkServerStatus_returnsDegradedStatus_correctly() {
        Server server = new Server("web-02", "web-02", ServerStatus.DEGRADED, "production");
        when(serverRepository.findByName("web-02")).thenReturn(Optional.of(server));

        String result = serverStatusTool.checkServerStatus("web-02");

        assertThat(result).contains("DEGRADED");
    }

    @Test
    void checkServerStatus_returnsHelpfulError_whenServerNotFound() {
        when(serverRepository.findByName("unknown-server")).thenReturn(Optional.empty());
        when(serverRepository.findAll()).thenReturn(List.of(
                new Server("web-01", "web-01", ServerStatus.UP, "production"),
                new Server("db-01", "db-01", ServerStatus.DOWN, "production")
        ));

        String result = serverStatusTool.checkServerStatus("unknown-server");

        assertThat(result)
                .contains("No server found with name 'unknown-server'")
                .contains("web-01")
                .contains("db-01");
    }
}