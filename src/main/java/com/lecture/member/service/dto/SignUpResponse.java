package com.lecture.member.service.dto;

import com.lecture.member.domain.Member;

public record SignUpResponse(
        long id
) {

    public SignUpResponse(Member member) {
        this(member.getId());
    }
}
