package com.lecture.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @DisplayName("이름의 앞,뒤 공백은 제거된다.")
    @Test
    void createNameWithoutBlank() {
        // given
        String name = "  name  ";
        Name expected = new Name("name");

        // when
        Name result = new Name(name);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("이름은 1자 이상, 10자 이하로 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void createName(int count) {
        // given
        String name = "가".repeat(count);

        // when & then
        assertThatNoException().isThrownBy(() -> new Name(name));
    }

    @DisplayName("이름은 1자 미만, 10자 초과이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 11})
    void cannotCreateInvalidName(int count) {
        // given
        String name = "가".repeat(count);

        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(LectureException.class)
                .hasMessage("이름은 1자 이상, 10자 이하로 설정해주세요.");
    }

    @DisplayName("이름이 누락되면 예외가 발생한다.")
    @Test
    void cannotCreateInvalidNullName() {
        // given
        String name = null;

        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(LectureException.class)
                .hasMessage("이름은 1자 이상, 10자 이하로 설정해주세요.");
    }
}
