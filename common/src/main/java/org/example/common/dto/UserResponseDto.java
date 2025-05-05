package org.example.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Data
public class UserResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long idCompany;
    private String companyName;
}
