package com.lecture.course.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.lecture.fixture.CourseFixture;
import com.lecture.fixture.MemberFixture;
import com.lecture.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

class CourseTest {

    @DisplayName("강좌를 생성할 수 있다.")
    @Test
    void createCourse() {
        // given
        Member member = new Member("linirini", "linirini@email.com", "01012345678", "password1", "TEACHER");
        String title = "너나위의 내집마련 기초반";
        long capacity = 10;
        long price = 200_000;

        // when
        assertThatNoException().isThrownBy(() -> new Course(title, capacity, price, member));
    }

    @DisplayName("강좌가 수강 신청이 되었으면 수강 인원수와 수강 신청률을 갱신한다.")
    @Test
    void enrolled() {
        // given
        Course course = CourseFixture.create(8, MemberFixture.createTeacher());

        // when
        course.enrolled();

        // then
        assertAll(
                () -> assertThat(course.getEnrollCount()).isEqualTo(1),
                () -> assertThat(course.getEnrollRatio()).isEqualTo(12.5)
        );
    }
}
