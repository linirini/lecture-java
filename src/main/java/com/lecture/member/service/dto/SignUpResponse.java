package com.lecture.member.service.dto;

import com.lecture.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 성공 시 응답 본문 형식입니다.")
public record SignUpResponse(
        @Schema(example = "1")
        long id
) {

    public SignUpResponse(Member member) {
        this(member.getId());
    }
}
