package com.capstone.skone.chat.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom implements Serializable {
    @Id
    private String roomName;
    private String roomId;
}
