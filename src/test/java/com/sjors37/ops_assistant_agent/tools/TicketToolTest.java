package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.Ticket;
import com.sjors37.ops_assistant_agent.model.enums.TicketStatus;
import com.sjors37.ops_assistant_agent.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketToolTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketTool ticketTool;

    @Test
    void createTicket_returnsConfirmationWithTicketId() {
        Ticket ticket = new Ticket("TICKET-1", "web-02", "Connection pool exhausted", "Details", TicketStatus.OPEN);
        when(ticketRepository.create("web-02", "Connection pool exhausted", "Details")).thenReturn(ticket);

        String result = ticketTool.createTicket("web-02", "Connection pool exhausted", "Details");

        assertThat(result).contains("TICKET-1").contains("web-02").contains("Connection pool exhausted");
    }

    @Test
    void createTicket_delegatesToRepositoryWithCorrectArguments() {
        Ticket ticket = new Ticket("TICKET-1", "db-01", "DB down", "Connection refused", TicketStatus.OPEN);
        when(ticketRepository.create("db-01", "DB down", "Connection refused")).thenReturn(ticket);

        ticketTool.createTicket("db-01", "DB down", "Connection refused");

        verify(ticketRepository).create("db-01", "DB down", "Connection refused");
    }
}