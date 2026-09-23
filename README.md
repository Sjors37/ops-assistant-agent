# Spring AI Ops Agent

An agentic AI assistant built with **Spring Boot 4** and **Spring AI 2.0** that can check server status, search logs, and file tickets by calling tools autonomously based on natural language requests.

## Why this project

Most agentic AI examples are written in Python. This project explores what building **tool-calling agents in the Java/Spring ecosystem** looks like using Spring AI — a good fit for teams that already run production Java services and want to add AI-driven automation without leaving their stack.

## What it does

The agent has three tools at its disposal, and decides itself which ones to call (and in what order) based on the user's message:

- **`checkServerStatus`** — looks up whether a server is `UP`, `DOWN`, or `DEGRADED`
- **`searchLogs`** — searches recent logs for a server, optionally filtered by severity (`INFO`/`WARN`/`ERROR`)
- **`createTicket`** — files a support ticket for an issue

A single request like *"Check web-02, find out why it's degraded, and file a ticket"* can trigger all three tools in sequence — the agent chains them itself, nothing is hardcoded.

## Tech stack

- **Java 21**
- **Spring Boot 4.0**
- **Spring AI 2.0** (Anthropic model integration)
- **Maven**
- **Docker** / Docker Compose

## Getting started

### Prerequisites
- Java 21+
- Maven
- An Anthropic API key
- (Optional) Docker, if you want to run it in a container instead

### Option 1: Run locally

1. Clone the repo:
   ```bash
   git clone https://github.com/Sjors37/ops-assistant-agent.git
   cd ops-assistant-agent
   ```

2. Set your API key as an environment variable:

   **macOS / Linux:**
   ```bash
   export ANTHROPIC_API_KEY=your-key-here
   ```
   **Windows (PowerShell):**
   ```powershell
   $env:ANTHROPIC_API_KEY = "your-key-here"
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Option 2: Run with Docker

```bash
export ANTHROPIC_API_KEY=your-key-here
docker compose up --build
```

### Try it out

```bash
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Check web-02, find out why it is degraded, and file a ticket"}'
```

Available mock servers to experiment with: `web-01`, `web-02`, `db-01`, `staging-01`.

## Known limitations

This is a demo project, built to explore agentic patterns rather than to be production-ready:

- **No conversation memory.** Each request to `/chat` is stateless. If the agent asks a follow-up question (e.g. "want me to file a ticket?"), it can't process a confirmation sent as a separate message — the full instruction needs to be in one message. Adding memory (e.g. Spring AI's `MessageChatMemoryAdvisor`) would be the natural next step.
- **In-memory mock data.** Servers, logs, and tickets are hardcoded/in-memory rather than backed by a real database.


