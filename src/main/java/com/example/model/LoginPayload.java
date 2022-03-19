package com.example.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginPayload implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}