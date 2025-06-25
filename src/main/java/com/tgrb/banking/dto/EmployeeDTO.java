package com.tgrb.banking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.NotFound;

@Data
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "FirstName cannot be null.")
    @NotEmpty(message = "FirstName cannot be empty.")
    @NotBlank(message = "FirstName cannot be blank.")
    private String firstName;

    @NotNull(message = "LastName cannot be null.")
    @NotEmpty(message = "LastName cannot be empty.")
    @NotBlank(message = "LastName cannot be blank.")
    private String lastName;

    @NotNull(message = "Email cannot be null.")
    @NotEmpty(message = "Email cannot be empty.")
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;
}
