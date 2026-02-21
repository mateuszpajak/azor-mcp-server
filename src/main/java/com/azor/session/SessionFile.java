package com.azor.session;

import java.time.Instant;

public record SessionFile(String sessionId,
                          String sessionName,
                          Instant lastModified
) {
}