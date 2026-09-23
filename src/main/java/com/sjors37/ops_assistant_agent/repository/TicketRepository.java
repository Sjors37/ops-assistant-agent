package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.Ticket;
import com.sjors37.ops_assistant_agent.model.enums.TicketStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TicketRepository {

    private final Map<String, Ticket> tickets = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Ticket create(String serverName, String title, String description) {
        String id = "TICKET-" + idCounter.getAndIncrement();
        Ticket ticket = new Ticket(id, serverName, title, description, TicketStatus.OPEN);
        tickets.put(id, ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return List.copyOf(tickets.values());
    }
}