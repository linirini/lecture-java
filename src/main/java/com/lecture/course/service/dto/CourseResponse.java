package com.lecture.course.service.dto;

import com.lecture.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 목록 중 개별 강의에 해당하는 응답 형식입니다.")
public record CourseResponse(
        @Schema(example = "1")
        long id,
        @Schema(example = "너나위의 내집마련 기초반")
        String title,
        @Schema(example = "200000")
        long price,
        @Schema(example = "홍길동")
        String teacher,
        @Schema(example = "10")
        long currentEnrollment,
        @Schema(example = "15")
        long capacity
) {

    public CourseResponse(Course course) {
        this(
                course.getId(),
                course.getTitle().getTitle(),
                course.getPrice().getPrice(),
                course.getMember().getName().getName(),
                course.getEnrollCount(),
                course.getCapacity().getCapacity()
        );
    }
}
