package com.capstone.skone.chat.infrastructure;

import com.capstone.skone.chat.domain.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatRoom, String> {
}
