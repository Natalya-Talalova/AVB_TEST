package org.example.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyResponceDto implements Serializable {
    @NotBlank(message = "Company name cannot be blank")
    private String name;
    private BigDecimal budget;
    private List<UserResponseDto> employees;
    private Long id;

    public List<UserResponseDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserResponseDto> employees) {
        this.employees = employees;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
