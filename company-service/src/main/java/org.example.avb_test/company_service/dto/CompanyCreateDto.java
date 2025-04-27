package org.example.avb_test.company_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyCreateDto implements Serializable {
    private String name;
    private BigDecimal budget;
    private List<Long> employeeIds;
}
