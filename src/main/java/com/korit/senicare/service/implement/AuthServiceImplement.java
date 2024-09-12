package com.korit.senicare.service.implement;



import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.korit.senicare.common.util.AuthNumberCreator;
import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.request.auth.SignInRequestDto;
import com.korit.senicare.dto.request.auth.SignUpRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.auth.SignInResponseDto;
import com.korit.senicare.entity.NurseEntity;
import com.korit.senicare.entity.TelAuthNumberEntity;
import com.korit.senicare.provider.JwtProvider;
import com.korit.senicare.provider.SmsProvider;
import com.korit.senicare.repository.NurseRepository;
import com.korit.senicare.repository.TelAuthNumberRepository;
import com.korit.senicare.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 레포지토리 쳐다보게 하는 어노테이션
public class AuthServiceImplement implements AuthService {

    
    private final SmsProvider smsProvider;
    private final JwtProvider jwtProvider;

    private final NurseRepository nurseRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;


    //5. 클라이언트로 부터 입력받은 password 암호화 작업 실행
    // 구현체 만들기(직접 주입이라 final 사용하지 않음)
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {

        String userId = dto.getUserId();

        try {

            boolean isExistedId = nurseRepository.existsByUserId(userId);
            if (isExistedId) return ResponseDto.duplicatedUserId();
  

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto) {
    
        // 1. dto에서 telnumber를 꺼내온다
        String telNumber = dto.getTelNumber();

        try {
            boolean isExistedTelnumber = nurseRepository.existsByTelNumber(telNumber);
            if (isExistedTelnumber) return ResponseDto.duplicatedTelNumber();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        String authNumber = AuthNumberCreator.number4();

        boolean isSendSuccess = smsProvider.sendMessage(telNumber, authNumber);
        if (!isSendSuccess) return ResponseDto.messageSendFail();

        try {

            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(telNumber, authNumber);
            telAuthNumberRepository.save(telAuthNumberEntity);
            
        } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    
    }

    @Override
    public ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto) {
        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        try {

            boolean isMatched = telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatched) return ResponseDto.telAuthFail();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        
        }

        return ResponseDto.success();


    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {
    
  
        String userId = dto.getUserId();
        String password = dto.getPassword();
        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        try {
            boolean isExistedId = nurseRepository.existsByUserId(userId);
            if(isExistedId) return ResponseDto.duplicatedUserId();

            boolean isExistedTelnumber = nurseRepository.existsByTelNumber(telNumber);
            if(isExistedTelnumber) return ResponseDto.duplicatedTelNumber();

            //데이터베이스의 tel_auth_number 테이블에서 telNumber 와 authNumber를 모두 가지고 있는 레코드 조회
            boolean isMatched = telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            // 4.1 만약 존재하는 레코드가 없다면 'TAF' 응답 처리
            if(!isMatched) return ResponseDto.telAuthFail();

            // 5. 클라이언트로 부터 입력받은 password 암호화 작업 실행
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            NurseEntity nurseEntity = new NurseEntity(dto);
            nurseRepository.save(nurseEntity);

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
       
        String userId = dto.getUserId();
        String password = dto.getPassword();

        String accessToken = null;

        try {

           NurseEntity nurseEntity = nurseRepository.findByUserId(userId);
           if(nurseEntity == null) return ResponseDto.signInFail();
        
           // 암호화된 비밀번호 &  평문 비밀번호 비교작업
           String encodedPassword = nurseEntity.getPassword();
           boolean isMatched = passwordEncoder.matches(password, encodedPassword);
           if (!isMatched) return ResponseDto.signInFail();

           accessToken = jwtProvider.create(userId);
           if (accessToken == null) return ResponseDto.tokenCreateFail();
            
        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError(); 
        }

        return SignInResponseDto.success(accessToken);

    }

}
