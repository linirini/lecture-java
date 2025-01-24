package com.lecture.course.service;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import com.lecture.ServiceSliceTest;
import com.lecture.course.repository.CourseRepository;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseReadRequest;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.course.service.dto.CourseResponse;
import com.lecture.course.service.dto.CourseResponses;
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

    static Stream<Arguments> sortConditionProvider() {
        return Stream.of(
                Arguments.of(OrderColumn.RECENT, new Long[]{4L, 3L, 1L, 2L}),
                Arguments.of(OrderColumn.MOST, new Long[]{2L, 1L, 4L, 3L}),
                Arguments.of(OrderColumn.HIGHEST, new Long[]{1L, 2L, 4L, 3L})
        );
    }

    static Stream<Arguments> pageProvider() {
        return Stream.of(
                Arguments.of(-1, 2, new Long[]{4L, 3L}),
                Arguments.of(0, 2, new Long[]{4L, 3L}),
                Arguments.of(3, 2, new Long[]{})
        );
    }

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
        Member member = memberRepository.save(MemberFixture.createTeacher(new Email("member@email.com"), new PhoneNumber("01012345678")));
        Member other = memberRepository.save(MemberFixture.createTeacher(new Email("other@email.com"), new PhoneNumber("01087654321")));
        CourseRequest courseRequest = CourseRequestFixture.create();
        courseService.createCourse(courseRequest, other);

        // when & then
        assertThatNoException().isThrownBy(() -> courseService.createCourse(courseRequest, member));
    }

    @DisplayName("주어진 조건에 맞게 강의 목록을 정렬한다.")
    @ParameterizedTest
    @MethodSource("sortConditionProvider")
    @Sql("/course.sql")
    void readAllCoursesSorted(OrderColumn column, Long... courseIds) {
        // given
        CourseReadRequest courseReadRequest = new CourseReadRequest(1, 5, "DESC", column.name());

        // when
        CourseResponses courseResponses = courseService.readAllCourses(courseReadRequest);
        List<Long> result = courseResponses.courses().stream().map(CourseResponse::id).toList();

        // then
        assertThat(result).containsExactly(courseIds);
    }

    @DisplayName("주어진 페이지 번호에 맞게 강의 목록을 조회한다.")
    @ParameterizedTest
    @MethodSource("pageProvider")
    @Sql("/course.sql")
    void readAllCoursesByPage(int pageNo, int pageSize, Long... courseIds) {
        // given
        CourseReadRequest courseReadRequest = new CourseReadRequest(pageNo, pageSize, null, null);

        // when
        CourseResponses courseResponses = courseService.readAllCourses(courseReadRequest);
        List<Long> result = courseResponses.courses().stream().map(CourseResponse::id).toList();

        // then
        assertThat(result).containsExactly(courseIds);
    }
}
