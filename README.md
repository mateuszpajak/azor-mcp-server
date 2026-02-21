# Azor MCP Server

A Quarkus-based MCP (Model Context Protocol) server that exposes chat session data from the Azor application. It allows AI assistants to list, read, and delete Azor chat sessions stored on the local filesystem.

## What it does

The server exposes three tools over the MCP protocol via STDIO:

| Tool | Description |
|------|-------------|
| `listChatSessions` | Lists all Azor chat session files with their last modification date, sorted by file name. |
| `getChatSessions` | Returns metadata and full conversation content of sessions. Accepts an optional list of session IDs or names to filter results; returns all sessions if no filter is provided. |
| `deleteChatSession` | Deletes a session file identified by session name or session ID. Returns whether the deletion was successful. |

Session files are read from the directory configured via the `azor.home.path` property. Only files ending with `-log.json` are recognized as session files.

## Requirements

- **Java 25**
- Gradle Wrapper is included — no separate Gradle installation needed

## Build

```sh
./gradlew clean build -Dquarkus.package.jar.type=uber-jar
```

The output JAR will be at `build/azor-mcp-server-1.0.0-runner.jar`.

## Manual test

The server communicates over **STDIO** and expects JSON-RPC messages, so it cannot be tested by running it directly in a terminal. Use [MCP Inspector](https://github.com/modelcontextprotocol/inspector):

```sh
npx @modelcontextprotocol/inspector java -jar build/azor-mcp-server-1.0.0-runner.jar
```
