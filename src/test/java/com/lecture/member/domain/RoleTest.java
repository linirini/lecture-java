package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RoleTest {

    @DisplayName("존재하지 않는 역할의 이름으로 찾으려 할 경우 예외가 발생한다.")
    @Test
    void findByNameWithInvalidRole() {
        // given
        String invalidRoleName = "INVALID";

        // when & then
        assertThatThrownBy(() -> Role.findByName(invalidRoleName))
                .isInstanceOf(LectureException.class)
                .hasMessage("존재하지 않는 역할입니다.");
    }

    @DisplayName("대소문자 구분 없이 이름으로 역할을 찾을 수 있다.")
    @ParameterizedTest
    @EnumSource
    void findByNameIgnoreCase(Role role) {
        // given
        String roleName = role.name().toLowerCase();

        // when
        Role result = Role.findByName(roleName);

        // then
        assertThat(result).isEqualTo(role);
    }
}
