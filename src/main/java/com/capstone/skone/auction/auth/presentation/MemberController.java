package com.capstone.skone.auction.auth.presentation;

import com.capstone.skone.auction.auth.application.MemberService;
import com.capstone.skone.auction.auth.dto.MemberDto;
import com.capstone.skone.auction.auth.board.application.BoardService;
import com.capstone.skone.auction.auth.board.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final BoardService boardService;
  /**
   * 메인 페이지 이동
   * @return
   */
  @GetMapping("/")
  public String main(Model model,
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
          Pageable pageable) {
    System.out.println("============> login success ");
    Page<Board> mainBoard = boardService.pageList(pageable);

    int start = (int) (Math.floor(mainBoard.getNumber()/10)*10 +1);
    int last = Math.min(start + 9, mainBoard.getTotalPages());

    model.addAttribute("mainBoard", mainBoard);
    model.addAttribute("start", start);
    model.addAttribute("last", last);

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
    return "login/scone_sign_up";
  }

  /**
   * 회원가입 처리
   * @param memberDto
   * @return
   */
  @PostMapping("/signup")
  public String signup(@ModelAttribute("memberDto") MemberDto memberDto,  HttpServletRequest request)throws Exception {


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