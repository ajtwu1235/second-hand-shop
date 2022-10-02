package com.capstone.skone.auth.presentation;

import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.auth.dto.MemberDto;
import com.capstone.skone.board.application.BoardService;
import com.capstone.skone.board.application.FileService;
import com.capstone.skone.board.domain.Board;
import com.capstone.skone.board.dto.request.CreateFileRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final BoardService boardService;
  private final FileService fileService;
  /**
   * 메인 페이지 이동
   * @return
   */
  @GetMapping("/")
  public String main(Model model) {
    List<Board> allBoard = boardService.findAllBoard();
    List<CreateFileRequest> files = new ArrayList<>();
    for(int i=0;i<allBoard.size();i++){
      files.add(fileService.getFile(allBoard.get(i).getFileId()));
    }
    model.addAttribute("allBoard", allBoard);
    model.addAttribute("files", files);
    return "scone_main";
  }

  /**
   * 로그인 페이지 이동
   * @return
   */
  @GetMapping("/user/login")
  public String goLogin() {
    return "login/login";
  }

  /**
   * 로그인 에러
   * @param model
   * @return
   */
  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);

    return "/login/login";
  }

  /**
   * 회원가입 페이지 이동
   * @return
   */
  @GetMapping("/signup")
  public String goSignup() {
    return "login/signup";
  }

  /**
   * 회원가입 처리
   * @param memberDto
   * @return
   */
  @PostMapping("/signup")
  public String signup(MemberDto memberDto) {
    memberService.joinUser(memberDto);

    return "redirect:/user/login";
  }

  /**
   * 접근 거부 페이지 이동
   * @return
   */
  @GetMapping("/denied")
  public String doDenied() {
    return "login/denied";
  }

  /**
   * 내 정보 페이지 이동
   * @return
   */
  @GetMapping("/info")
  public String goMyInfo() {
    return "login/myinfo";
  }

  /**
   * Admin 페이지 이동
   * @return
   */
  @GetMapping("/admin")
  public String goAdmin() {
    return "login/admin";
  }
}