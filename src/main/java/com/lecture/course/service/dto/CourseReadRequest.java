package com.lecture.course.service.dto;

public record CourseReadRequest(
        long page,
        String sort
) {
}
