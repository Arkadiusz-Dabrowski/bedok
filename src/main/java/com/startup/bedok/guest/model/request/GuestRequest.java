package com.startup.bedok.guest.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GuestRequest {
    private final String name;
    private final Integer age;
    private final String language;
}
