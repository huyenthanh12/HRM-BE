package com.example.HRM.BE.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
