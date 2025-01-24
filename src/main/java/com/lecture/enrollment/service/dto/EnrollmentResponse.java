package com.lecture.enrollment.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "개별 수강 신청 성공/실패 여부 응답 형식입니다.")
public record EnrollmentResponse(
        @Schema(example = "1")
        long courseId,
        @Schema(example = "SUCCESS")
        String status,
        @Schema(example = "수강 신청에 성공하였습니다.")
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
