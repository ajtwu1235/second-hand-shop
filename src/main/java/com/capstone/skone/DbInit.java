
package com.capstone.skone;

import com.capstone.skone.auth.application.MemberService;
import com.capstone.skone.auth.dto.MemberDto;
import com.capstone.skone.board.application.BoardService;
import com.capstone.skone.board.dto.CreateBoardDto;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final MemberService memberService;
    private final BoardService boardService;

    @Bean
    private void AuctionInit() {

        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("1234");
        memberDto.setPassword("1234");
        memberDto.setNickname("현");
        memberDto.setAuth("ROLE_MEMBER");

        MemberDto memberDto2 = new MemberDto();
        memberDto2.setEmail("123");
        memberDto2.setPassword("123");
        memberDto2.setNickname("하림");
        memberDto2.setAuth("ROLE_MEMBER");

        memberService.joinUser(memberDto);
        memberService.joinUser(memberDto2);

    }
}