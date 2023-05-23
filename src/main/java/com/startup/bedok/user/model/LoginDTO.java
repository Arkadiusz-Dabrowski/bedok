package com.startup.bedok.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginDTO {
    private final String email;
    private final String phoneNumber;
    private final String password;
}
