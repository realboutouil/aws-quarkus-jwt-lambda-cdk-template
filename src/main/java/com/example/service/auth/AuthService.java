package com.example.service.auth;

import com.example.dto.UserInfo;
import com.example.model.LoginPayload;
import com.example.model.LoginResponse;

public interface AuthService {

    LoginResponse authenticate(LoginPayload payload);

    UserInfo userinfo();

    String user_roles();
}
