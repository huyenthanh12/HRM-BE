package com.example.HRM.BE.DTO;

import com.example.HRM.BE.entities.SkillEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Category {

    int id;

    @NotEmpty
    @NotBlank
    String name;

    String status;

}
