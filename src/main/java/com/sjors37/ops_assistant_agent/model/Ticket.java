package com.sjors37.ops_assistant_agent.model;

import com.sjors37.ops_assistant_agent.model.enums.TicketStatus;

public record Ticket(
        String id,
        String serverName,
        String title,
        String description,
        TicketStatus status
) {}