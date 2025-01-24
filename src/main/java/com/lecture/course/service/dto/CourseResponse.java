package com.lecture.course.service.dto;

import com.lecture.course.domain.Course;

public record CourseResponse(
        long id,
        String title,
        long price,
        String teacher,
        long currentEnrollment,
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
