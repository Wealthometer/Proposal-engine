package com.love.events.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.love.events.dto.EventView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AffectionEventConsumer {

    private static final String RECENT_EVENTS_KEY = "love-stack:recent-events";
    private static final int MAX_RECENT = 50;

    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    public AffectionEventConsumer(StringRedisTemplate redis, ObjectMapper objectMapper) {
        this.redis = redis;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "affection-event-stream", groupId = "affection-event-service")
    public void onEvent(String message) {
        String[] parts = message.split("\\|", 2);
        String type = parts.length > 0 ? parts[0] : "unknown";
        String payload = parts.length > 1 ? parts[1] : message;

        EventView event = new EventView(type, payload, System.currentTimeMillis());
        try {
            String json = objectMapper.writeValueAsString(event);
            redis.opsForList().leftPush(RECENT_EVENTS_KEY, json);
            redis.opsForList().trim(RECENT_EVENTS_KEY, 0, MAX_RECENT - 1);
            log.info("consumed affection event: type={} payload={}", type, payload);
