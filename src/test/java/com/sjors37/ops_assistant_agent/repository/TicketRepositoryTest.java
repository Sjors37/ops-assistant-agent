package com.sjors37.ops_assistant_agent.repository;

import com.sjors37.ops_assistant_agent.model.Ticket;
import com.sjors37.ops_assistant_agent.model.enums.TicketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRepositoryTest {

    private TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository = new TicketRepository();
    }

    @Test
    void create_returnsTicketWithOpenStatus() {
        Ticket ticket = ticketRepository.create("web-02", "Connection pool exhausted", "Details here");

        assertThat(ticket.status()).isEqualTo(TicketStatus.OPEN);
        assertThat(ticket.serverName()).isEqualTo("web-02");
        assertThat(ticket.title()).isEqualTo("Connection pool exhausted");
    }

    @Test
    void create_generatesUniqueIncrementingIds() {
        Ticket first = ticketRepository.create("web-02", "Issue 1", "Description 1");
        Ticket second = ticketRepository.create("db-01", "Issue 2", "Description 2");

        assertThat(first.id()).isEqualTo("TICKET-1");
        assertThat(second.id()).isEqualTo("TICKET-2");
    }

    @Test
    void findAll_returnsAllCreatedTickets() {
        ticketRepository.create("web-02", "Issue 1", "Description 1");
        ticketRepository.create("db-01", "Issue 2", "Description 2");

        List<Ticket> result = ticketRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findAll_returnsEmptyList_whenNoTicketsCreated() {
        List<Ticket> result = ticketRepository.findAll();

        assertThat(result).isEmpty();
    }
}