package com.capstone.skone.chat.application;


import com.capstone.skone.chat.domain.Message;
import com.capstone.skone.chat.dto.ChatMessage;
import com.capstone.skone.chat.dto.ChatMessageDto;
import com.capstone.skone.chat.infrastructure.MessageRepository;
import com.capstone.skone.util.domain.BaseTimeEntity;
import io.netty.util.collection.CharObjectMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void setMessage(ChatMessage chatMessage) {
        messageRepository.save(
            Message.builder()
                .id(chatMessage.getRoomId())
                .sender(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .time(LocalDateTime.now())
                .build()
        );
    }
}
