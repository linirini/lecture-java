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

class EnrollmentServiceTest extends ServiceSliceTest {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CourseRepository courseRepository;


    @DisplayName("성공적으로 수강 신청을 처리한다.")
    @Test
    void enroll() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course course = courseRepository.save(CourseFixture.create(1, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(course.getId()));

        // when
        EnrollmentResponses responses = enrollmentService.enrollAll(enrollmentRequest, member).join();

        // then
        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(1),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("SUCCESS")
        );
    }

    @DisplayName("존재하지 않는 강좌에 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfCourseNotFound() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course course = courseRepository.save(CourseFixture.create(1, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(course.getId()));

        // when
        EnrollmentResponses responses = enrollmentService.enrollAll(enrollmentRequest, member).join();

        // then
        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(1),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("FAILURE"),
                () -> assertThat(responses.enrollments().get(0).message()).isEqualTo("요청하신 강좌를 찾을 수 없어요.")
        );
    }

    @DisplayName("최대 수강 인원을 초과하는 강좌에 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfMaxCapacityExceeded() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Member other = memberRepository.save(MemberFixture.createStudent());
        Course course = courseRepository.save(CourseFixture.create(1, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(course.getId()));

        // when
        enrollmentService.enrollAll(enrollmentRequest, other).join();
        EnrollmentResponses responses = enrollmentService.enrollAll(enrollmentRequest, member).join();

        // then
        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(1),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("FAILURE"),
                () -> assertThat(responses.enrollments().get(0).message()).isEqualTo("이미 최대 수강 가능 인원을 초과했습니다.")
        );
    }

    @DisplayName("이미 등록된 강좌에 반복해서 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfAlreadyEnrolled() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course course = courseRepository.save(CourseFixture.create(1, member));
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(List.of(course.getId()));

        // when
        enrollmentService.enrollAll(enrollmentRequest, member).join();
        EnrollmentResponses responses = enrollmentService.enrollAll(enrollmentRequest, member).join();

        // then
        assertAll(
                () -> assertThat(responses.enrollments()).hasSize(1),
                () -> assertThat(responses.enrollments().get(0).status()).isEqualTo("FAILURE"),
                () -> assertThat(responses.enrollments().get(0).message()).isEqualTo("이미 수강 신청이 완료된 강좌입니다.")
        );
    }

}
