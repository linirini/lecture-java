package com.lecture.course.service;

import org.springframework.stereotype.Service;
import com.lecture.course.domain.Course;
import com.lecture.course.repository.CourseRepository;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseIdResponse createCourse(CourseRequest courseRequest, Member member) {
        Course course = courseRequest.toCourse(member);
        courseRepository.save(course);
        return new CourseIdResponse(course);
    }
}
