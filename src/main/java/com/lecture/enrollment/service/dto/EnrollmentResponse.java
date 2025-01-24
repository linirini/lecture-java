package com.lecture.enrollment.service.dto;

public record EnrollmentResponse(
        long courseId,
        String status,
        String message
) {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";
    private static final String SUCCESS_MESSAGE = "수강신청이 완료되었습니다.";

    public static EnrollmentResponse succeed(Long courseId) {
        return new EnrollmentResponse(courseId, SUCCESS, SUCCESS_MESSAGE);
    }

    public static EnrollmentResponse fail(Long courseId, String message) {
        return new EnrollmentResponse(courseId, FAILURE, message);
    }
}
