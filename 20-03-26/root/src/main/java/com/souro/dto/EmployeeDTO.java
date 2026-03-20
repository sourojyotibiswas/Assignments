package com.souro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Name cannot be empty")
    private String employeeName;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    private String gender;

    private String designation;
    @Positive(message = "Salary must be greater than 0")
    private double salary;

    private String dateOfJoining;

    private AddressDTO address;
}
