package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PhoneNumberTest {

    @DisplayName("전화번호의 앞,뒤 공백은 제거된다.")
    @Test
    void createPhoneNumberWithoutBlank() {
        // given
        String phoneNumber = "  01012345678  ";
        PhoneNumber expected = new PhoneNumber("01012345678");

        // when
        PhoneNumber result = new PhoneNumber(phoneNumber);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("전화번호 형식에 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "가"})
    void cannotCreateInvalidPhoneNumber(String phoneNumber) {
        // when & then
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(LectureException.class)
                .hasMessage("전화번호 형식이 유효하지 않아요.");
    }
}
