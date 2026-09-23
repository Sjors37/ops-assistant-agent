package com.sjors37.ops_assistant_agent.model;

import com.sjors37.ops_assistant_agent.model.enums.ServerStatus;

public record Server(
        String id,
        String name,
        ServerStatus status,
        String environment
) {}