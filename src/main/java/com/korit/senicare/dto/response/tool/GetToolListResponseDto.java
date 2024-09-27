package com.korit.senicare.dto.response.tool;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.senicare.common.object.Tool;
import com.korit.senicare.dto.response.ResponseCode;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.ResponseMessage;
import com.korit.senicare.entity.ToolEntity;

import lombok.Getter;

// 코드와 메시지 가져오기
@Getter
public class GetToolListResponseDto extends ResponseDto {
    private List<Tool> tools;

    private GetToolListResponseDto(List<ToolEntity> toolEntities) {
        super(ResponseCode.SUCCCESS, ResponseMessage.SUCCESS);
        this.tools = Tool.getList(toolEntities);

    }

    public static ResponseEntity<GetToolListResponseDto> success(List<ToolEntity> toolEntities) {
        GetToolListResponseDto responseBody = new GetToolListResponseDto(toolEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
