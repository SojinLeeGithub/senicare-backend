package com.korit.senicare.dto.request.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    // 필드 생성

    @NotBlank
    @Length(max=5)
    private String name;
    
    @NotBlank
    @Length(max=20)
    private String userId;
    
    @NotBlank
    //^$ => 문자열의 시작과 끝을 알림
    // 사용자의 비밀번호 (8~13자의 영문 + 숫자)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$")
    private String password;
    
    @NotBlank
    // 사용자의 전화번호 (11자의 숫자)
    @Pattern(regexp = "^[0-9]{11}$")
    private String telNumber;

    @NotBlank
    private String authNumber;
    
    @Pattern(regexp = "^(home|kakao|naver)$")
    @NotBlank
    private String joinPath;
 
    private String snsId;
    
}
