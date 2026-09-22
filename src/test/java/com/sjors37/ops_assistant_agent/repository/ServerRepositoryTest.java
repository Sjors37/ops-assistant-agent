package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.Server;
import com.sjors37.ops_assistant_agent.model.ServerStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ServerRepositoryTest {

    private ServerRepository serverRepository;

    @BeforeEach
    void setUp() {
        serverRepository = new ServerRepository();
    }

    @Test
    void findByName_returnsServer_whenExists() {
        Optional<Server> result = serverRepository.findByName("web-01");

        assertThat(result).isPresent();
        assertThat(result.get().status()).isEqualTo(ServerStatus.UP);
    }

    @Test
    void findByName_isCaseInsensitive() {
        Optional<Server> result = serverRepository.findByName("WEB-01");

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("web-01");
    }

    @Test
    void findByName_returnsEmpty_whenServerDoesNotExist() {
        Optional<Server> result = serverRepository.findByName("nonexistent-server");

        assertThat(result).isEmpty();
    }

    @Test
    void findAll_returnsAllMockServers() {
        List<Server> result = serverRepository.findAll();

        assertThat(result).hasSize(4);
        assertThat(result).extracting(Server::name)
                .containsExactlyInAnyOrder("web-01", "web-02", "db-01", "staging-01");
    }
}