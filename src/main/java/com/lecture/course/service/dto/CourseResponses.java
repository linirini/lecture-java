package com.lecture.course.service.dto;

import java.util.List;
import org.springframework.data.domain.Slice;
import com.lecture.course.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 목록 응답 형식입니다.")
public record CourseResponses(List<CourseResponse> courses) {

    public static CourseResponses from(Slice<Course> courses) {
        return new CourseResponses(courses.stream().map(CourseResponse::new).toList());
    }
}
