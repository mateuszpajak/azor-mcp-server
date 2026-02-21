package com.azor.session;

import java.util.List;

public record ChatSession(
        String sessionId,
        String model,
        String systemRole,
        String agent,
        String sessionName,
        String fileName,
        List<ChatMessage> history
) {
}
