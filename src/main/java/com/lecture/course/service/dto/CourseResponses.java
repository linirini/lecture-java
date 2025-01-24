package com.lecture.course.service.dto;

import java.util.List;
import org.springframework.data.domain.Slice;
import com.lecture.course.domain.Course;

public record CourseResponses(List<CourseResponse> courses) {

    public static CourseResponses from(Slice<Course> courses) {
        return new CourseResponses(courses.stream().map(CourseResponse::new).toList());
    }
}
