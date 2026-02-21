package com.azor.session;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class ChatSessionParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ChatSession parseSession(Path path) {
        try {
            var node = OBJECT_MAPPER.readTree(path.toFile());

            var sessionId = node.path("session_id").asText("");
            var model = node.path("model").asText("");
            var systemRole = node.path("system_role").asText("");
            var agent = node.path("agent").asText("");
            var sessionName = node.path("session_name").asText("");
            var fileName = path.getFileName().toString();

            var history = new ArrayList<ChatMessage>();
            var historyNode = node.path("history");
            if (historyNode.isArray()) {
                for (var msgNode : historyNode) {
                    var role = msgNode.path("role").asText("");
                    var timestamp = msgNode.path("timestamp").asText("");
                    var text = msgNode.path("text").asText("");
                    history.add(new ChatMessage(role, timestamp, text));
                }
            }

            return new ChatSession(sessionId, model, systemRole, agent, sessionName, fileName, history);
        } catch (IOException e) {
            return null;
        }
    }
}
