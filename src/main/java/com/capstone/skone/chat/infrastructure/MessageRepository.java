package com.capstone.skone.chat.infrastructure;

import com.capstone.skone.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {
}
