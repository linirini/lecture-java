package com.lecture.course.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.lecture.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThatNoException;

class CourseTest {

    @DisplayName("강좌를 생성할 수 있다.")
    @Test
    void createMember() {
        // given
        Member member = new Member("linirini", "linirini@email.com", "01012345678", "password1", "TEACHER");
        String title = "너나위의 내집마련 기초반";
        long capacity = 10;
        long price = 200_000;

        // when
        assertThatNoException().isThrownBy(() -> new Course(title, capacity, price, member));
    }
}
