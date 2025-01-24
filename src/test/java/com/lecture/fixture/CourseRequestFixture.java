package com.lecture.fixture;

import com.lecture.course.service.dto.CourseRequest;

public class CourseRequestFixture {
    public static CourseRequest create() {
        return new CourseRequest("title", 10L, 20000L);
    }

    public static CourseRequest create(String title) {
        return new CourseRequest(title, 10L, 20000L);
    }

    public static CourseRequest create(String title, long capacity) {
        return new CourseRequest(title, capacity, 20000L);
    }
}
