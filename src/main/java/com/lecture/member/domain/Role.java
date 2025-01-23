package com.lecture.member.domain;

import java.util.Arrays;
import com.lecture.exception.LectureException;

public enum Role {
    STUDENT,
    TEACHER,
    ;

    private static final String UNKNOWN_ROLE_MESSAGE = "존재하지 않는 역할입니다.";

    public static Role findByName(String name) {
        return Arrays.stream(values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new LectureException(UNKNOWN_ROLE_MESSAGE));
    }
}
