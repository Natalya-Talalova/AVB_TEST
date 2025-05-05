package org.example.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Data
public class CompanyResponceDto implements Serializable {
    private String name;
    private BigDecimal budget;
    private List<UserResponseDto> employees;
    private Long id;
}
