package com.lecture.course.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.lecture.exception.LectureException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @DisplayName("수강료는 0원 이상으로 생성할 수 있다.")
    @Test
    void createPrice() {
        // given
        long price = 0;

        // when & then
        assertThatNoException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("수강료는 0원 미만으로 설정하면 예외가 발생한다.")
    @Test
    void cannotCreateInvalidPrice() {
        // given
        long price = -1;

        // when & then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(LectureException.class)
                .hasMessage("수강료는 0원 이상으로 설정해주세요.");
    }
}
