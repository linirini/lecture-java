package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class MemberTest {

    @DisplayName("회원을 생성할 수 있다.")
    @Test
    void createMember() {
        // given
        String name = "linirini";
        String email = "linirini@email.com";
        String phoneNumber = "01012345678";
        String password = "password1";
        String role = "STUDENT";

        // when
        assertThatNoException().isThrownBy(() -> new Member(name, email, phoneNumber, password, role));
    }
}
