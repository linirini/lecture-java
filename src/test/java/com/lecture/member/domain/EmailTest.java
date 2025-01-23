package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일의 앞,뒤 공백은 제거된다.")
    @Test
    void createEmailWithoutBlank() {
        // given
        String email = "  member@email.com  ";
        Email expected = new Email("member@email.com");

        // when
        Email result = new Email(email);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("이메일 형식에 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "가"})
    void cannotCreateInvalidEmail(String email) {
        // when & then
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(LectureException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }
}
