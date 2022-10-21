package com.capstone.skone.auction.auth.application;

import com.capstone.skone.auction.auth.domain.member.Member;
import com.capstone.skone.auction.auth.dto.MemberDto;
import com.capstone.skone.auction.auth.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
  private final MemberRepository memberRepository;

  /**
   * 회원가입 처리
   * @param memberDto
   * @return
   */
  @Transactional
  public Long joinUser(MemberDto memberDto) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 암호화 처리
    memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

    return memberRepository.save(memberDto.toEntity()).getId();
  }


  /**
   * 상세 정보 조회
   * Security 지정 서비스이므로 필수 구현
   * @param email
   * @return 사용자의 계정정보와 권한을 갖는 UserDetails 인터페이스 반환
   * @throws UsernameNotFoundException
   */
  @Override
  public Member loadUserByUsername(String email) throws UsernameNotFoundException {
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException((email)));
  }

  public Member loadUserByUserId(Long userId){
    return memberRepository.findById(userId).get();
  }


}
