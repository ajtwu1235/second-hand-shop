package com.capstone.skone.chat.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(timeToLive = 30, value = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;

    private String message;

    private String sender;

    private LocalDateTime time;
}