package com.korit.senicare.dto.response.nurse;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.response.ResponseCode;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.ResponseMessage;
import com.korit.senicare.entity.NurseEntity;

import lombok.Getter;

@Getter
public class GetSignInResponseDto extends ResponseDto {

    private String userId;
    private String name;
    private String telNumbr;

    //(NurseEntity nurseEntity) : 굳이 하나하나 받아올 필요 없으므로 통으로 받아와서 코드를 단순화 하였다.
    public GetSignInResponseDto(NurseEntity nurseEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = nurseEntity.getUserId();
        this.name = nurseEntity.getName();
        this.telNumbr = nurseEntity.getTelNumber();
    }

    public static ResponseEntity<GetSignInResponseDto> success(NurseEntity nurseEntity) {
        GetSignInResponseDto responseBody = new GetSignInResponseDto(nurseEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }
    
}
