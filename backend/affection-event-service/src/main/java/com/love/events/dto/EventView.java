package com.love.events.dto;

public record EventView(String type, String payload, long receivedAtEpochMs) {
}
