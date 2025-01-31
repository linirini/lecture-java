package com.lecture.enrollment.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.course.domain.Course;
import com.lecture.course.repository.CourseRepository;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.fixture.CourseFixture;
import com.lecture.fixture.MemberFixture;
import com.lecture.member.domain.Member;
import com.lecture.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EnrollmentFacadeTest extends ServiceSliceTest {

    @Autowired
    private EnrollmentFacade enrollmentFacade;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CourseRepository courseRepository;

    @DisplayName("두 개의 강좌를 성공적으로 신청할 수 있다.")
    @Test
    void enrollMultipleCourses() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course course1 = courseRepository.save(CourseFixture.create(1, member));
        Course course2 = courseRepository.save(CourseFixture.create(1, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(course1.getId(), course2.getId()));

        // when
        EnrollmentResponses responses = enrollmentFacade.enrollAll(enrollmentRequest, member).join();

        // then
        Course afterCourse1 = courseRepository.findById(course1.getId()).get();
        Course afterCourse2 = courseRepository.findById(course2.getId()).get();

        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(2),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("SUCCESS"),
                () -> assertThat(responses.enrollments().get(1).status()).isEqualTo("SUCCESS"),
                () -> assertThat(afterCourse1.getEnrollCount()).isEqualTo(1),
                () -> assertThat(afterCourse2.getEnrollCount()).isEqualTo(1),
                () -> assertThat(afterCourse1.getEnrollRatio()).isEqualTo(100),
                () -> assertThat(afterCourse2.getEnrollRatio()).isEqualTo(100)
        );
    }

    @DisplayName("두 개의 강좌 중 하나가 실패하면, 성공한 강좌만 적용되고 실패한 강좌는 롤백된다.")
    @Test
    void enrollMultipleCoursesWithOneFailure() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course successfulCourse = courseRepository.save(CourseFixture.create(1, member));
        Course failedCourse = courseRepository.save(CourseFixture.create(2, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(failedCourse.getId()));
        enrollmentFacade.enrollAll(enrollmentRequest, member).join();

        EnrollmentRequest enrollmentRequestWithAlreadyEnrolled = new EnrollmentRequest(List.of(successfulCourse.getId(), failedCourse.getId()));

        // when
        EnrollmentResponses responses = enrollmentFacade.enrollAll(enrollmentRequestWithAlreadyEnrolled, member).join();

        // then
        Course afterSuccessfulCourse = courseRepository.findById(successfulCourse.getId()).get();
        Course afterFailedCourse = courseRepository.findById(failedCourse.getId()).get();

        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(2),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("SUCCESS"),
                () -> assertThat(responses.enrollments().get(1).status()).isEqualTo("FAILURE"),
                () -> assertThat(responses.enrollments().get(1).message()).isEqualTo("이미 수강 신청이 완료된 강좌입니다."),

                // 성공한 강좌 검증
                () -> assertThat(afterSuccessfulCourse.getEnrollCount()).isEqualTo(1),
                () -> assertThat(afterSuccessfulCourse.getEnrollRatio()).isEqualTo(100),

                // 실패한 강좌 검증
                () -> assertThat(afterFailedCourse.getEnrollCount()).isEqualTo(1),
                () -> assertThat(afterFailedCourse.getEnrollRatio()).isEqualTo(50)
        );
    }
}
