package com.korit.senicare.service;

import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.response.nurse.GetSignInResponseDto;

public interface NurseService {
    
    // extends > super :extends 가 좀 더 큰 범위
    ResponseEntity<? super GetSignInResponseDto> getSignIn(String userId);

}
