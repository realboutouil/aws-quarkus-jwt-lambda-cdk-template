package com.example.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPayload implements Serializable {

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @Email
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}