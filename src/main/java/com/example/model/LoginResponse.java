package com.example.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Instant expires;

    private String access_token;
}