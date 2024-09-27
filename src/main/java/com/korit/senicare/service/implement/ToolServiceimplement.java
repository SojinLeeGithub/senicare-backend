package com.korit.senicare.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.senicare.dto.request.tool.PatchToolRequestDto;
import com.korit.senicare.dto.request.tool.PostToolRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.tool.GetToolListResponseDto;
import com.korit.senicare.dto.response.tool.GetToolResponseDto;
import com.korit.senicare.entity.ToolEntity;
import com.korit.senicare.repository.ToolRepository;
import com.korit.senicare.service.ToolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToolServiceimplement implements ToolService{

    // 의존성 주입
    private final ToolRepository toolRepository;

    @Override
    public ResponseEntity<ResponseDto> postTool(PostToolRequestDto dto) {
       
        try {
            ToolEntity toolEntity = new ToolEntity(dto);
            toolRepository.save(toolEntity);
            
        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<? super GetToolListResponseDto> getToolList() {
        List<ToolEntity> toolEntities = new ArrayList<>();
        try {
            
           toolEntities = toolRepository.findByOrderByToolNumberDesc();

        
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetToolListResponseDto.success(toolEntities);
    }

    @Override
    public ResponseEntity<? super GetToolResponseDto> getTool(Integer toolNumber) {

       // ? 내부에서 가져올 수 없어서 외부에서 선언한다. 왜지?
        ToolEntity toolEntity = null;
       
        // 단일 일 떄는 조회 했을 때 아무것도 없으면 에러 메시지 반환. 리스트형 일 때는 빈값이어도 아무것도 반환하지 않음
        try {

        toolEntity = toolRepository.findByToolNumber(toolNumber);
        if (toolEntity == null) return ResponseDto.noExistTool();
       } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    
       }
       return GetToolResponseDto.success(toolEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> patchTool(Integer toolNumber, PatchToolRequestDto dto) {

        try {
            ToolEntity toolEntity = toolRepository.findByToolNumber(toolNumber);
            if (toolEntity == null) return ResponseDto.noExistTool();

            toolEntity.patch(dto);
            toolRepository.save(toolEntity);

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> deleteTool(Integer tooNumber) {
        try {
            ToolEntity toolEntity = toolRepository.findByToolNumber(tooNumber);
            if(toolEntity == null) return ResponseDto.noExistTool();

            toolRepository.delete(toolEntity);
            
        } catch (Exception exception) {
           exception.printStackTrace();
           return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    

    
}
