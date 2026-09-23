package com.sjors37.ops_assistant_agent.tools;

import com.sjors37.ops_assistant_agent.model.Ticket;
import com.sjors37.ops_assistant_agent.repository.TicketRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TicketTool {

    private final TicketRepository ticketRepository;

    public TicketTool(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Tool(description = "Create a support ticket for a server issue. " +
            "Only use this after the user has explicitly confirmed they want a ticket created — " +
            "never call this proactively without confirmation.")
    public String createTicket(
            @ToolParam(description = "The name of the server the ticket is about, e.g. 'web-02'") String serverName,
            @ToolParam(description = "A short title summarizing the issue") String title,
            @ToolParam(description = "A detailed description of the issue, ideally including relevant log findings") String description
    ) {
        Ticket ticket = ticketRepository.create(serverName, title, description);
        return "Ticket %s created for server '%s': %s".formatted(ticket.id(), ticket.serverName(), ticket.title());
    }
}