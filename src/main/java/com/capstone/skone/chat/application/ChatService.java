package com.capstone.skone.chat.application;

import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.chat.domain.ChatRoom;
import com.capstone.skone.chat.dto.ChatRoomInfo;
import com.capstone.skone.chat.infrastructure.ChatRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
  private final ChatRepository chatRepository;

  //채팅방 불러오기
  public List<ChatRoomInfo> AllChatRooms() {
    List<ChatRoom> AllChatRooms = new ArrayList<>((Collection) chatRepository.findAll());
    List<ChatRoomInfo> result = new ArrayList<>();
    for(ChatRoom chatRoom: AllChatRooms){
      result.add(new ChatRoomInfo(chatRoom.getRoomId(), chatRoom.getRoomName()));
    }
    Collections.reverse(result);
    return result;
  }

  //채팅방 하나 불러오기
  public ChatRoomInfo searchChatRoom(String roomId) {
    Optional<ChatRoom> chatRoom = chatRepository.findById(roomId);
    return new ChatRoomInfo(chatRoom.get().getRoomId(), chatRoom.get().getRoomName());
  }

  //채팅방 생성
  public ChatRoomInfo createRoom(String name) {
    ChatRoomInfo chatRoomInfo = ChatRoomInfo.create(name);
    chatRepository.save(
        ChatRoom.builder()
            .roomName(chatRoomInfo.getRoomName())
            .roomId(chatRoomInfo.getRoomId())
            .build()
    );
    return chatRoomInfo;
  }
}