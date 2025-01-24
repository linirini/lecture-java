package com.lecture.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderColumnTest {

    @DisplayName("주어진 조건에 따라 정렬 컬럼을 반환한다.")
    @Test
    void findByCondition() {
        // given & when
        String recentColumn = OrderColumn.findByCondition("RECENT").getColumn();
        String mostColumn = OrderColumn.findByCondition("MOST").getColumn();
        String highestColumn = OrderColumn.findByCondition("HIGHEST").getColumn();

        // then
        assertAll(
                () -> assertThat(recentColumn).isEqualTo("createdAt"),
                () -> assertThat(mostColumn).isEqualTo("enrollCount"),
                () -> assertThat(highestColumn).isEqualTo("enrollRatio")
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
