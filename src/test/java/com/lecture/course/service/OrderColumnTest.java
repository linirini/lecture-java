package com.lecture.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderColumnTest {

    @DisplayName("주어진 조건에 따라 대소문자 구분 없이 정렬 컬럼을 반환한다.")
    @ParameterizedTest
    @CsvSource({"RECENT, createdAt", "MOST, enrollCount", "HIGHEST, enrollRatio"})
    void findByCondition(String column, String expected) {
        // given & when
        String result = OrderColumn.findByCondition(column).getColumn();
        String result2 = OrderColumn.findByCondition(column.toLowerCase()).getColumn();

        // then
        assertAll(
                () -> assertThat(result).isEqualTo(expected),
                () -> assertThat(result2).isEqualTo(expected)
        );
    }

    @DisplayName("존재하지 않는 조건을 입력하면 예외가 발생한다.")
    @Test
    void findDefaultByUnknownCondition() {
        assertThatThrownBy(() -> OrderColumn.findByCondition("UNKNOWN"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 정렬 조건입니다.");
    }
}
