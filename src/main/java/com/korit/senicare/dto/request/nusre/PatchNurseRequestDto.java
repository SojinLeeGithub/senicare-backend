package com.korit.senicare.dto.request.nusre;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchNurseRequestDto {
    @NotBlank
    private String name;

}
