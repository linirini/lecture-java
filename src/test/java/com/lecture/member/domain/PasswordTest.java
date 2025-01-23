package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PasswordTest {

    @DisplayName("비밀번호의 앞, 뒤 공백은 제거된다.")
    @Test
    void createPasswordWithoutBlank() {
        // given
        String password = "  Password1  ";
        Password expected = new Password("Password1");

        // when
        Password result = new Password(password);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("비밀번호는 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합해서 만들 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcDEF", "abc123", "ABC123"})
    void createPassword(String password) {
        // when & then
        assertThatNoException().isThrownBy(() -> new Password(password));
    }

    @DisplayName("비밀번호는 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcdef", "ABCDEF", "123456"})
    void cannotCreateInvalidPassword(String password) {
        // when & then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(LectureException.class)
                .hasMessage("비밀번호는 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합해야 합니다.");
    }

    @DisplayName("비밀번호 길이가 6자 이상, 10자 이하로 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"123abc", "12345abcde"})
    void createValidLengthPassword(String password) {
        // when & then
        assertThatNoException().isThrownBy(() -> new Password(password));
    }

    @DisplayName("비밀번호 길이가 6자 미만, 10자 초과일 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"123ab", "12345abcdef"})
    void cannotCreateInvalidLengthPassword(String password) {
        // when & then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(LectureException.class)
                .hasMessage("비밀번호는 최소 6자 이상, 10자 이하여야 합니다.");
    }
}
