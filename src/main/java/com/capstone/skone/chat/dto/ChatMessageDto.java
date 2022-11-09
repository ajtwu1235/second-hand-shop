package com.capstone.skone.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatMessageDto {
    //roomId= id;
    private String id;
    private String message;
    private String sender;
}