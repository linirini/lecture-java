package com.lecture.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.course.repository.CourseRepository;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.exception.ForbiddenException;
import com.lecture.exception.LectureException;
import com.lecture.fixture.CourseRequestFixture;
import com.lecture.fixture.MemberFixture;
import com.lecture.member.domain.Email;
import com.lecture.member.domain.Member;
import com.lecture.member.domain.PhoneNumber;
import com.lecture.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CourseServiceTest extends ServiceSliceTest {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("주어진 강의 정보로 새로운 강의를 생성한다.")
    @Test
    void createCourse() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        CourseRequest courseRequest = CourseRequestFixture.create();

        //when
        CourseIdResponse courseIdResponse = courseService.createCourse(courseRequest, member);

        //then
        assertAll(
                () -> assertThat(courseRepository.findAll()).hasSize(1),
                () -> assertThat(courseRepository.findById(courseIdResponse.id())).isPresent()
        );
    }

    @DisplayName("강의 등록 주체가 학생 회원이라면 예외가 발생한다.")
    @Test
    void cannotCreateCourseIfStudent() {
        // given
        Member member = memberRepository.save(MemberFixture.createStudent());
        CourseRequest courseRequest = CourseRequestFixture.create();

        // when & then
        assertThatThrownBy(() -> courseService.createCourse(courseRequest, member))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("요청하신 작업을 처리할 권한이 없어요.");
    }

    @DisplayName("강사가 이미 같은 이름의 강좌를 등록했다면 예외가 발생한다.")
    @Test
    void cannotCreateCourseIfDuplicated() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        CourseRequest courseRequest = CourseRequestFixture.create();
        courseService.createCourse(courseRequest, member);

        // when & then
        assertThatThrownBy(() -> courseService.createCourse(courseRequest, member))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 같은 이름의 강좌를 등록했어요. 다시 설정해주세요.");
    }

    @DisplayName("다른 강사가 등록한 강좌와 동일한 강좌명으로 등록할 수 있다.")
    @Test
    void createCourseIfOtherTeacherTitleDuplicated() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher(new Email("member@email.com"),new PhoneNumber("01012345678")));
        Member other = memberRepository.save(MemberFixture.createTeacher(new Email("other@email.com"),new PhoneNumber("01087654321")));
        CourseRequest courseRequest = CourseRequestFixture.create();
        courseService.createCourse(courseRequest, other);

        // when & then
        assertThatNoException().isThrownBy(() -> courseService.createCourse(courseRequest, member));
    }
}
