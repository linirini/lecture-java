package com.lecture.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import com.lecture.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 가입 요청 본문 형식입니다.")
public record SignUpRequest(
        @Schema(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @Schema(example = "user@email.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @Schema(example = "01012345678")
        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber,
        @Schema(example = "password1")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @Schema(example = "STUDENT")
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
