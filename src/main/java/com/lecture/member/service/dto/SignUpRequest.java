package com.lecture.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import com.lecture.member.domain.Member;

public record SignUpRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @NotBlank(message = "역할을 입력해주세요.")
        String role
) {

    public Member toMember() {
        return new Member(
                name,
                email,
                phoneNumber,
                password,
                role
        );
    }
}
