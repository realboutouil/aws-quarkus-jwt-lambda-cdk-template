package com.example.dto;

import com.example.entities.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String userId;

    private String username;

    @Builder.Default
    private List<ERole> roles = new ArrayList<>();

    private String full_name;

    private String firstName;

    private String lastName;
}
