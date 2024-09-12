package com.korit.senicare.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.response.ResponseCode;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String accessToken;
    private Integer expiration;

    // lombok사용 않고 직접 만듦
    // 내부에서만 사용되게 private으로 선언
    private SignInResponseDto(String accessToken) {

        // 부모 클래스에 빈 생성자가 없다면 super 사용해야 한다.
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.accessToken = accessToken;
        // 초단위로 표시(10시간 *  60분 * 60초)
        this.expiration = 10 * 60 * 60 ;

    }

    // static 매서드
    public static ResponseEntity<SignInResponseDto> success (String accessToken)
    {
        // 생성자 호출 (accessToken을 받아야 함)
        SignInResponseDto responseBody = new SignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
