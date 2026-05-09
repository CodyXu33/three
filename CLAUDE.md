# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **multi-person chat system** learning project with three components:
- **Java Chat Server** - Backend WebSocket/TCP server (currently in planning)
- **C# Desktop Client** - PC UI built with WPF/WinForms
- **React Native Mobile App** - Cross-platform mobile client

## Project Status

**Currently in planning phase** - No code has been written yet.

Key planning documents:
- `chat-server-plan.md` - Detailed Java chat server development plan with milestones, code architecture, and implementation guides
- `java-chat-server-timeline.md` - 21-day learning schedule

## Architecture

```
React Native App ←→ Java Chat Server ←→ C# Desktop Client
                    (WebSocket)
```

## Java Chat Server (chat-server-plan.md)

The server development follows a 4-milestone plan:

1. **Milestone 1**: Protocol design & basic framework
   - JSON message protocol with MessageType enum
   - ChatServer main class + ClientHandler per-client thread
   - User authentication (LOGIN, LOGOUT, REGISTER)

2. **Milestone 2**: Private messaging
   - Online user management via ServerContext
   - Message routing and delivery
   - User online/offline notifications

3. **Milestone 3**: Group chat
   - Group creation, join, leave
   - Group message broadcasting
   - GroupService for group operations

4. **Milestone 4**: Optimization & refactor
   - NIO migration (BIO → Reactor pattern)
   - Heartbeat detection mechanism
   - Exception handling improvements

## Development Approach

- **BIO first, then NIO** - Start with traditional blocking sockets to learn fundamentals, then refactor to NIO
- **JSON for messaging** - All messages use JSON format with type, from, to, content, timestamp fields
- **Each client = one thread** - BIO version uses thread-per-client model

## File Naming Conventions

- Java source: `UpperCamelCase.java`
- Java packages: `com.chat.*`
- Message types: `MessageType` enum with uppercase constants (LOGIN, PRIVATE_MSG, GROUP_MSG, etc.)