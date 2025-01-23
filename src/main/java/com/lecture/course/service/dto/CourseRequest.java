package com.lecture.course.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.lecture.course.domain.Course;
import com.lecture.member.domain.Member;

public record CourseRequest(
        @NotBlank(message = "강좌명을 입력해주세요.")
        String title,
        @NotNull(message = "최대 수강 인원을 입력해주세요.")
        Long capacity,
        @NotNull(message = "수강료를 입력해주세요.")
        Long price
) {

    public Course toCourse(Member member){
        return new Course(title,capacity,price, member);
    }
}
