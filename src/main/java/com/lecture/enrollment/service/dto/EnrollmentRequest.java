package com.lecture.enrollment.service.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(@NotNull(message = "강좌 정보를 입력해주세요.") List<Long> courseIds) {
}
