package com.lecture.enrollment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lecture.course.domain.Course;
import com.lecture.course.repository.CourseRepository;
import com.lecture.enrollment.domain.Enrollment;
import com.lecture.enrollment.repository.EnrollmentRepository;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponse;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.exception.LectureException;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    public static final String UNKNOWN_COURSE_MESSAGE = "요청하신 강좌를 찾을 수 없어요.";
    public static final String CAPACITY_EXCEEDED_MESSAGE = "이미 최대 수강 가능 인원을 초과했습니다.";
    public static final String ALREADY_ENROLLED_MESSAGE = "이미 수강 신청이 완료된 강좌입니다.";

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Async
    @Transactional
    public CompletableFuture<EnrollmentResponses> enrollAll(EnrollmentRequest enrollmentRequest, Member member) {
        List<EnrollmentResponse> enrollmentResponses = new ArrayList<>();
        for (Long courseId : enrollmentRequest.courseIds()) {
            EnrollmentResponse enrollmentResponse = enrollAll(member, courseId);
            enrollmentResponses.add(enrollmentResponse);
        }
        return CompletableFuture.completedFuture(new EnrollmentResponses(enrollmentResponses));
    }

    private EnrollmentResponse enrollAll(Member member, Long courseId) {
        try {
            Course course = getCourseById(courseId);
            validateIfCapacityExceeded(course);
            validateIfAlreadyEnrolled(course, member);
            enrollmentRepository.save(new Enrollment(member, course));
            return EnrollmentResponse.succeed(courseId);
        } catch (Exception e) {
            return EnrollmentResponse.fail(courseId, e.getMessage());
        }
    }

    private Course getCourseById(Long courseId) {
        return courseRepository.findByIdForUpdate(courseId)
                .orElseThrow(() -> new LectureException(UNKNOWN_COURSE_MESSAGE));
    }

    private void validateIfCapacityExceeded(Course course) {
        if (course.isFull()) {
            throw new LectureException(CAPACITY_EXCEEDED_MESSAGE);
        }
    }

    private void validateIfAlreadyEnrolled(Course course, Member member) {
        if (!enrollmentRepository.existsByMemberIdAndCourseId(member.getId(), course.getId())) {
            throw new LectureException(ALREADY_ENROLLED_MESSAGE);
        }
    }
}
