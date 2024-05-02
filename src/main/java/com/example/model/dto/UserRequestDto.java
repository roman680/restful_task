package com.example.model.dto;

import java.util.Date;

import com.example.validation.AgeConstraint;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Email(message = "Invalid email")
    @NotBlank(message = "Invalid Email: Empty email")
    @NotNull(message = "Invalid Email: Email is NULL")
    private String email;

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    private String firstName;

    @NotBlank(message = "Invalid Lastname: Empty lastname")
    @NotNull(message = "Invalid Lastname: Lastname is NULL")
    private String lastName;

    @NotBlank(message = "Invalid Date of birth: Empty date of birth")
    @NotNull(message = "Invalid  Date of birth: Date of birth is NULL")
    @AgeConstraint
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @Nullable
    private String address;

    @NotBlank(message = "Invalid Phone number: Empty number")
    @NotNull(message = "Invalid Phone number: Number is NULL")
    @Pattern(regexp="^^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",  message = "Invalid phone number: num" +
            "ber must much patter")
    private String phoneNumber;
}
