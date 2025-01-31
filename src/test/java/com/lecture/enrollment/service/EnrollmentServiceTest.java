package com.lecture.enrollment.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.course.domain.Course;
import com.lecture.course.repository.CourseRepository;
import com.lecture.exception.LectureException;
import com.lecture.fixture.CourseFixture;
import com.lecture.fixture.MemberFixture;
import com.lecture.member.domain.Member;
import com.lecture.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

        // when
        enrollmentService.enroll(member, course.getId());

        // then
        Course after = courseRepository.findById(course.getId()).get();
        assertAll(
                () -> assertThat(after.getEnrollCount()).isEqualTo(1),
                () -> assertThat(after.getEnrollRatio()).isEqualTo(100)
        );
    }

    @DisplayName("존재하지 않는 강좌에 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfCourseNotFound() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());

        // when & then
        assertThatThrownBy(() -> enrollmentService.enroll(member, 0L))
                .isInstanceOf(LectureException.class)
                .hasMessage("요청하신 강좌를 찾을 수 없어요.");
    }

    @DisplayName("최대 수강 인원을 초과하는 강좌에 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfMaxCapacityExceeded() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Member other = memberRepository.save(MemberFixture.createStudent());
        Course course = courseRepository.save(CourseFixture.create(1, member));

        // when
        enrollmentService.enroll(other, course.getId());

        // then
        assertThatThrownBy(() -> enrollmentService.enroll(member, course.getId()))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 최대 수강 가능 인원을 초과했습니다.");
    }

    @DisplayName("이미 등록된 강좌에 반복해서 수강 신청 시 예외가 발생한다.")
    @Test
    void cannotEnrollIfAlreadyEnrolled() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        Course course = courseRepository.save(CourseFixture.create(2, member));

        // when
        enrollmentService.enroll(member, course.getId());

        // then
        assertThatThrownBy(() -> enrollmentService.enroll(member, course.getId()))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 수강 신청이 완료된 강좌입니다.");
    }

    @DisplayName("동시에 수강 신청을 시도할 때 최대 수강 신청 인원을 넘으면 수강 신청에 실패한다.")
    @Test
    void enrollConcurrently() {
        // given
        Member teacher = memberRepository.save(MemberFixture.createTeacher());
        Member student = memberRepository.save(MemberFixture.createStudent());
        Course course = courseRepository.save(CourseFixture.create(1, teacher));
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // when
        Future<?> future = executorService.submit(() -> enrollmentService.enroll(teacher, course.getId()));
        Future<?> future2 = executorService.submit(() -> enrollmentService.enroll(student, course.getId()));

        // then
        assertThatThrownBy(() -> {
            future.get();
            future2.get();
        })
                .hasCauseInstanceOf(LectureException.class)
                .hasMessageContaining("이미 최대 수강 가능 인원을 초과했습니다.");
        Course result = courseRepository.findById(course.getId()).get();
        assertAll(
                () -> assertThat(result.getEnrollCount()).isEqualTo(1),
                () -> assertThat(result.getEnrollRatio()).isEqualTo(100)
        );
    }
}
