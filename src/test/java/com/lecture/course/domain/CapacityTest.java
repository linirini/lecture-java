package com.lecture.course.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CapacityTest {

    @DisplayName("최대 수강 인원을 1명 이상으로 생성할 수 있다.")
    @Test
    void createCapacity() {
        // given
        long capacity = 1;

        // when & then
        assertThatNoException().isThrownBy(() -> new Capacity(capacity));
    }

    @DisplayName("최대 수강 인원을 1명 미만으로 설정하면 예외가 발생한다.")
    @Test
    void cannotCreateInvalidCapacity() {
        // given
        long capacity = 0;

        // when & then
        assertThatThrownBy(() -> new Capacity(capacity))
                .isInstanceOf(LectureException.class)
                .hasMessage("최대 수강 인원은 1명 이상으로 설정해주세요.");
    }
}
