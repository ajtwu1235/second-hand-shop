package com.capstone.skone.chat.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfo {

  private String roomId;
  private String roomName;


  public static ChatRoomInfo create(String name) {
    ChatRoomInfo room = new ChatRoomInfo();
    room.roomId = UUID.randomUUID().toString();
    room.roomName = name;
    return room;
  }
}
