package com.korit.senicare.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.senicare.entity.ToolEntity;

import lombok.Getter;


@Getter
public class Tool {
    private Integer toolNumber;
    private String name;
    private String purpose;
    private Integer count;


    // 생성자
    private Tool(ToolEntity toolEntity) {
        this.toolNumber = toolEntity.getToolNumber();
        this.name = toolEntity.getName();
        this.purpose = toolEntity.getPurpose();
        this.count = toolEntity.getCount();
    }

    public static List<Tool> getList(List<ToolEntity> toolEntities) {
        
        // 빈 배열 생성 (배열 아닐 떄는 null)
        List<Tool> tools = new ArrayList<>();
        for (ToolEntity toolEntity: toolEntities) {
            Tool tool = new Tool(toolEntity);
            tools.add(tool);
        }
        return tools;

    }

}
