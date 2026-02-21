package com.azor.session;

public record ChatMessage(String role,
                          String timestamp,
                          String text
) {
}