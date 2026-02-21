package com.azor.tools;

import com.azor.AzorHome;
import com.azor.session.ChatSessionParser;
import com.azor.session.SessionFile;
import io.quarkiverse.mcp.server.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListChatSessionsTool {

    @Inject
    AzorHome azorHome;

    @Tool(description = "Wyświetla listę wszystkich istniejących sesji czatu Azor wraz z ich ID, nazwą sesji i datą ostatniej modyfikacji, posortowanych według daty modyfikacji.")
    List<SessionFile> listChatSessions() {
        return azorHome.listSessionFiles().stream()
                .map(path -> {
                    var session = ChatSessionParser.parseSession(path);
                    if (session == null) return null;
                    return new SessionFile(session.sessionId(), session.sessionName(), getLastModifiedTime(path));
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(SessionFile::lastModified))
                .collect(Collectors.toList());
    }

    private static Instant getLastModifiedTime(Path path) {
        try {
            return Files.getLastModifiedTime(path).toInstant();
        } catch (IOException e) {
            return Instant.EPOCH;
        }
    }
}
