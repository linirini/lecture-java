package com.lecture.enrollment.service.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수강 신청 성공/실패 여부 응답 형식입니다.")
public record EnrollmentResponses(List<EnrollmentResponse> enrollments) {
}
