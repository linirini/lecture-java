package com.lecture.course.service.dto;

import com.lecture.course.domain.Course;

public record CourseIdResponse(long id) {
    public CourseIdResponse(Course course){
        this(course.getId());
    }
}
