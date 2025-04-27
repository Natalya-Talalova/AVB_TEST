package org.example.avb_test.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserCreateDto implements Serializable {
    @Size(message = "First name must be between 1 and 50 characters", min = 1, max = 50)
    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @Size(message = "Last name must be between 1 and 50 characters", min = 1, max = 50)
    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    @NotBlank(message = "Phone number should not be empty")
    private String phoneNumber;

    private Long idCompany;
}
