package org.example.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long idCompany;
    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
