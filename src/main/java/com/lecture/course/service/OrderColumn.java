package com.lecture.course.service;

import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderColumn {

    RECENT("createdAt"),
    MOST("enrollCount"),
    HIGHEST("enrollRatio")
    ;

    private final String column;

    public static String findByCondition(String condition) {
        OrderColumn orderColumn = Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(condition))
                .findFirst()
                .orElse(RECENT);
        return orderColumn.column;
    }
}
