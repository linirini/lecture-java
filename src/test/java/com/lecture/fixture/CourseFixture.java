package com.lecture.fixture;

import com.lecture.course.domain.Course;
import com.lecture.member.domain.Member;

public class CourseFixture {
    public static Course create(int capacity, Member member) {
        return new Course("title", capacity, 20000, member);
    }
}
