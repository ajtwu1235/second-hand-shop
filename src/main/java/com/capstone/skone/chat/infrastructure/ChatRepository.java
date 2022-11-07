package com.capstone.skone.chat.infrastructure;

import com.capstone.skone.chat.domain.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findChatRoomByRoomName(String roomName);

    Optional<ChatRoom> findByRoomId(String roomId);
}
