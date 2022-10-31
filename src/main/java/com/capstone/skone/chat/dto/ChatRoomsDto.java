package com.capstone.skone.chat.dto;

import com.capstone.skone.chat.domain.ChatRoom;
import java.util.Map;
import lombok.Data;

@Data
public class ChatRoomsDto {
    private Map<String, ChatRoom> chatRooms;

    public void put(String roomId, ChatRoom chatRoom) {
        chatRooms.put(roomId, chatRoom);
    }
}
