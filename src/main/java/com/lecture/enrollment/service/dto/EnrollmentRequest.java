package com.lecture.enrollment.service.dto;

import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수강 신청 요청 형식입니다.")
public record EnrollmentRequest(
        @Schema(description = "중복된 강의 ID는 한 번만 수강 신청 요청이 발생합니다.", example = "[1,2,3]")
        @Size(min = 1, message = "강좌 정보를 입력해주세요.")
        List<Long> courseIds
) {

    public EnrollmentRequest {
        if (Objects.isNull(courseIds)) {
            courseIds = List.of();
        }
        courseIds = courseIds.stream().distinct().toList();
    }
}
