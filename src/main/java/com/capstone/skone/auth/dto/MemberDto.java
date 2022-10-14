package com.capstone.skone.auth.dto;


import com.capstone.skone.auth.domain.member.Member;
import com.capstone.skone.auth.domain.member.vo.Gender;
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
    private String browser;
    private String password;
    private String auth;
    private String userName;
    private String nickname;
    private String year;
    private String month;
    private String date;
    //private String birth;
    private Gender gender;

    public Member toEntity() {
        return Member.builder()
                .email(email+"@"+browser)
                .password(password)
                .auth(auth)
                .userName(userName)
                .nickname(nickname)
                .birth(year+"."+month+"."+date)
                .gender(gender)
                .build();
    }
}