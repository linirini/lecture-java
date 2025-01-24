package com.lecture.enrollment.service.dto;

public record EnrollmentResponse(
        long courseId,
        String status,
        String message
) {
}
