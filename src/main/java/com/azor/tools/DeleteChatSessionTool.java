package com.azor.tools;

import com.azor.AzorHome;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;

import static com.azor.session.ChatSessionParser.parseSession;

@ApplicationScoped
public class DeleteChatSessionTool {

    @Inject
    AzorHome azorHome;

    @Tool(description = "Usuwa plik sesji czatu Azor identyfikowany po nazwie sesji lub ID sesji. Zwraca informację, czy plik został pomyślnie usunięty.")
    DeletionResult deleteChatSession(
            @ToolArg(description = "Nazwa sesji lub ID sesji do usunięcia.")
            String sessionIdentifier
    ) {
        var matchingPath = azorHome.listSessionFiles()
                .stream()
                .filter(path -> {
                    var session = parseSession(path);
                    return session != null && (
                            session.sessionName().equalsIgnoreCase(sessionIdentifier) ||
                                    session.sessionId().equalsIgnoreCase(sessionIdentifier));
                })
                .findFirst();

        if (matchingPath.isEmpty()) {
            return new DeletionResult(false);
        }

        try {
            Files.delete(matchingPath.get());
            return new DeletionResult(true);
        } catch (IOException e) {
            return new DeletionResult(false);
        }
    }

    record DeletionResult(boolean deleted) {
    }
}
