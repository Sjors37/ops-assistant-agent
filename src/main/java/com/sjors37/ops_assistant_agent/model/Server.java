package com.sjors37.ops_assistant_agent.model;

public record Server(
        String id,
        String name,
        ServerStatus status,
        String environment
) {}