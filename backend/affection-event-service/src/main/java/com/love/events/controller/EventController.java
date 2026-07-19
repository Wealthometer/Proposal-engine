package com.love.events.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final String RECENT_EVENTS_KEY = "love-stack:recent-events";

    private final StringRedisTemplate redis;

    public EventController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @GetMapping("/recent")
    public List<String> recent() {
        List<String> events = redis.opsForList().range(RECENT_EVENTS_KEY, 0, -1);
        return events == null ? List.of() : events;
    }
}
