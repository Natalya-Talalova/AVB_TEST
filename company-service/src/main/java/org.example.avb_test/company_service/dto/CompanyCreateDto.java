package org.example.avb_test.company_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyCreateDto implements Serializable {
    @NotBlank(message = "Company name cannot be blank")
    private String name;
    @NotNull(message = "Budget cannot be null")
    @PositiveOrZero(message = "Budget must be positive or zero")
    private BigDecimal budget;
    @NotEmpty(message = "Employee list cannot be empty")
    private List<Long> employeeIds;
}
