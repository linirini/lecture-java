package com.lecture.course.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.lecture.course.domain.Course;
import com.lecture.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 등록 요청 본문 형식입니다.")
public record CourseRequest(
        @Schema(example = "너나위의 내집마련 기초반")
        @NotBlank(message = "강좌명을 입력해주세요.")
        String title,
        @Schema(example = "10")
        @NotNull(message = "최대 수강 인원을 입력해주세요.")
        Long capacity,
        @Schema(example = "200000")
        @NotNull(message = "수강료를 입력해주세요.")
        Long price
) {

    public Course toCourse(Member member) {
        return new Course(title, capacity, price, member);
    }
}
