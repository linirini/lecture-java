package com.lecture.course.service;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderColumn {

    RECENT("createdAt"),
    MOST("enrollCount"),
    HIGHEST("enrollRatio"),
    ;

    private static final String UNKNOWN_ORDER_MESSAGE = "존재하지 않는 정렬 조건입니다.";
    private final String column;

    public static OrderColumn findByCondition(String condition) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(condition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(UNKNOWN_ORDER_MESSAGE));
    }
}
