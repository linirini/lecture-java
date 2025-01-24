package com.lecture.course.service;

import org.springframework.stereotype.Service;
import com.lecture.course.domain.Course;
import com.lecture.course.repository.CourseRepository;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseReadRequest;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.course.service.dto.CourseResponses;
import com.lecture.exception.ForbiddenException;
import com.lecture.exception.LectureException;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private static final String DUPLICATED_TITLE_MESSAGE = "이미 같은 이름의 강좌를 등록했어요. 다시 설정해주세요.";

    private final CourseRepository courseRepository;

    @Transactional
    public CourseIdResponse createCourse(CourseRequest courseRequest, Member member) {
        validateRole(member);
        Course course = courseRequest.toCourse(member);
        validateDuplicated(course);
        courseRepository.save(course);
        return new CourseIdResponse(course);
    }

    private void validateRole(Member member) {
        if (member.isStudent()) {
            throw new ForbiddenException();
        }
    }

    private void validateDuplicated(Course course) {
        if (courseRepository.existsByMemberAndTitle(course.getMember(), course.getTitle())) {
            throw new LectureException(DUPLICATED_TITLE_MESSAGE);
        }
    }

    public CourseResponses readAllCourses(CourseReadRequest courseReadRequest) {
        return null;
    }
}
