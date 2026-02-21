package com.azor.tools;

import io.quarkiverse.mcp.server.Tool;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class TimeTool {

    @Tool(description = "Zwraca aktualną datę i godzinę.")
    String getCurrentDateTime() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}