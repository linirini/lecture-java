package com.lecture.course.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TitleTest {

    @DisplayName("제목의 앞,뒤 공백은 제거된다.")
    @Test
    void createTitleWithoutBlank() {
        // given
        String email = "  너나위의 내집마련 기초반  ";
        Title expected = new Title("너나위의 내집마련 기초반");

        // when
        Title result = new Title(email);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("제목은 1자 이상, 50자 이하로 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 50})
    void createTitle(int count) {
        // given
        String title = "가".repeat(count);

        // when & then
        assertThatNoException().isThrownBy(() -> new Title(title));
    }

    @DisplayName("제목은 1자 미만, 50자 초과이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 51})
    void cannotCreateInvalidLengthTitle(int count) {
        // given
        String title = "가".repeat(count);

        // when & then
        assertThatThrownBy(() -> new Title(title))
                .isInstanceOf(LectureException.class)
                .hasMessage("제목은 1자 이상, 50자 이하로 설정해주세요.");
    }

    @DisplayName("제목이 누락되면 예외가 발생한다.")
    @Test
    void cannotCreateInvalidNullTitle() {
        // given
        String title = null;

        // when & then
        assertThatThrownBy(() -> new Title(title))
                .isInstanceOf(LectureException.class)
                .hasMessage("제목은 1자 이상, 50자 이하로 설정해주세요.");
    }
}
