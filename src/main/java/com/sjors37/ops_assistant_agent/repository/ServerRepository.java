package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.Server;
import com.sjors37.ops_assistant_agent.model.enums.ServerStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ServerRepository {

    //Mock-data
    private final Map<String, Server> servers = List.of(
            new Server("web-01", "web-01", ServerStatus.UP, "production"),
            new Server("web-02", "web-02", ServerStatus.DEGRADED, "production"),
            new Server("db-01", "db-01", ServerStatus.DOWN, "production"),
            new Server("staging-01", "staging-01", ServerStatus.UP, "staging")
    ).stream().collect(Collectors.toMap(Server::id, s -> s));

    public Optional<Server> findByName(String name) {
        return servers.values().stream()
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Server> findAll() {
        return List.copyOf(servers.values());
    }
}
