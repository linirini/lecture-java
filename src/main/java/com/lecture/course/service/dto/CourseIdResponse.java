package com.lecture.course.service.dto;

import com.lecture.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 등록에 성공했을 때 응답 본문 형식입니다.")
public record CourseIdResponse(
        @Schema(example = "1")
        long id
) {
    public CourseIdResponse(Course course){
        this(course.getId());
    }
}
