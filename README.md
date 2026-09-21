# Spring AI Ops Agent

An agentic AI assistant built with **Spring Boot 4** and **Spring AI 2.0** that can check server status, search logs, and file tickets by calling tools autonomously based on natural language requests.

## Why this project

Most agentic AI examples are written in Python. This project explores what building **tool-calling agents in the Java/Spring ecosystem** looks like using Spring AI — a good fit for teams that already run production Java services and want to add AI-driven automation without leaving their stack.

## Tech stack

- **Java 21**
- **Spring Boot 4.0**
- **Spring AI 2.0** (Anthropic model integration)
- Maven



## Getting started

### Prerequisites
- Java 21+
- Maven
- An Anthropic API key

### Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/Sjors37/ops-assistant-agent.git
   cd spring-ai-ops-agent
   ```

2. Set your API key as an environment variable:
   ```bash
   export ANTHROPIC_API_KEY=your-key-here
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Send a request to the agent:
   ```bash
   curl -X POST http://localhost:8080/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Is server web-01 healthy?"}'
   ```

