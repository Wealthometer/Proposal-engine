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

