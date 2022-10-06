package com.capstone.skone.auth.dto;


import com.capstone.skone.auth.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

  private String email;
  private String password;
  private String auth;

  public Member toEntity(){
    return Member.builder()
        .email(email)
        .password(password)
        .auth(auth)
        .build();
  }
}