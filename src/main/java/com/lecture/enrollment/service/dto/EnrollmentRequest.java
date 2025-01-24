package com.lecture.enrollment.service.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수강 신청 요청 형식입니다.")
public record EnrollmentRequest(
        @Schema(example = "[1,2,3]")
        @NotNull(message = "강좌 정보를 입력해주세요.")
        List<Long> courseIds
) {
}
