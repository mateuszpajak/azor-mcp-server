package com.azor.tools;

import com.azor.AzorHome;
import com.azor.session.ChatSession;
import com.azor.session.ChatSessionParser;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class GetChatSessionsTool {

    @Inject
    AzorHome azorHome;

    @Tool(description = """
            Zwraca metadane i pełną zawartość rozmów sesji czatu Azor.
            Jeśli sessionIdentifiers jest podane, zwracane są tylko sesje pasujące po ID sesji lub nazwie sesji.
            Jeśli sessionIdentifiers jest puste lub niepodane, zwracane są wszystkie sesje.
            """)
    List<ChatSession> getChatSessions(
            @ToolArg(description = "Opcjonalna lista identyfikatorów sesji (ID sesji lub nazwy sesji) do filtrowania wyników. Pozostaw puste, aby pobrać wszystkie sesje.", required = false)
            List<String> sessionIdentifiers
    ) {
        var sessions = azorHome.listSessionFiles().stream()
                .map(ChatSessionParser::parseSession)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (sessionIdentifiers == null || sessionIdentifiers.isEmpty()) {
            return sessions;
        }

        return sessions.stream()
                .filter(session -> sessionIdentifiers.stream().anyMatch(id ->
                        session.sessionId().equalsIgnoreCase(id) ||
                                session.sessionName().equalsIgnoreCase(id)))
                .collect(Collectors.toList());
    }
}
