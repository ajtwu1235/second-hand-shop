package com.capstone.skone.chat.presentaion;

import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.chat.application.ChatService;

import com.capstone.skone.chat.dto.ChatRoomInfo;
import com.capstone.skone.chat.dto.ChatRoomNameDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("chat")
public class ChatRoomController {
  private final ChatService chatService;

  // 채팅 리스트 화면
  @GetMapping("room")
  public String rooms() {
    return "chat/room";
  }

  // 모든 채팅방 목록 반환
  @GetMapping("rooms")
  @ResponseBody
  public List<ChatRoomInfo> room() {
    return chatService.AllChatRooms();
  }

  //채팅방 생성
  @PostMapping(value = "room", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ChatRoomInfo createRoom(@RequestBody ChatRoomNameDto name) {
    return chatService.createRoom(name.getName());
  }

  // 채팅방 입장 화면
  @GetMapping("room/enter/{roomId}")
  public String roomDetail(Model model, @PathVariable String roomId) {
    String nickname =  MemberService.currentUserNickname();
    System.out.println("여기 지나긴하냐?");
    model.addAttribute("nickname", nickname);
    model.addAttribute("roomId", roomId);
    return "chat/roomdetail";
  }

  // 특정 채팅방 조회
  @GetMapping("room/{roomId}")
  @ResponseBody
  public ChatRoomInfo roomInfo(@PathVariable String roomId) {
    return chatService.searchChatRoom(roomId);
  }
}