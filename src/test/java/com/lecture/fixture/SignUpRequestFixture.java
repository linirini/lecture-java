package com.lecture.fixture;

import com.lecture.member.service.dto.SignUpRequest;

public class SignUpRequestFixture {

    public static SignUpRequest create(){
        return new SignUpRequest("name", "user@email.com", "01012345678", "password1", "STUDENT");
    }
}
