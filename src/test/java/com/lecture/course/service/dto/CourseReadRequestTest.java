package com.lecture.course.service.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.lecture.course.service.OrderColumn;

import static org.assertj.core.api.Assertions.assertThat;

class CourseReadRequestTest {

    @DisplayName("CourseReadRequest를 Pageable로 변환한다.")
    @Test
    void toPageable() {
        // given
        int pageNo = 2;
        int pageSize = 10;
        Direction direction = Direction.ASC;
        OrderColumn orderColumn = OrderColumn.MOST;
        CourseReadRequest request = new CourseReadRequest(pageNo, pageSize, direction.name(), orderColumn.name());
        Pageable expected = PageRequest.of(pageNo - 1, pageSize, direction, orderColumn.getColumn());

        // when
        Pageable pageable = request.toPageable();

        // then
        assertThat(pageable).isEqualTo(expected);
    }

    @DisplayName("CourseReadRequest의 기본값 테스트")
    @Test
    void defaultValues() {
        // given
        CourseReadRequest courseReadRequest = new CourseReadRequest(null, null, null, null);
        Pageable expected = PageRequest.of(0, 20, Direction.DESC, OrderColumn.RECENT.getColumn());

        // when
        Pageable pageable = courseReadRequest.toPageable();

        // then
        assertThat(pageable).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 orderType이 주어진 경우 기본값 DESC를 사용한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"unknown"})
    void useDefaultOrderTypeWhenInvalid(String orderType) {
        // given
        OrderColumn orderColumn = OrderColumn.RECENT;
        CourseReadRequest request = new CourseReadRequest(1, 10, orderColumn.name(), orderType);
        Sort expected = Sort.by(Direction.DESC, orderColumn.getColumn());

        // when
        Pageable pageable = request.toPageable();

        // then
        assertThat(pageable.getSort()).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 orderColumn이 주어진 경우 기본값 RECENT를 사용한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"unknown"})
    void useDefaultOrderColumnWhenInvalid(String orderColumn) {
        // given
        CourseReadRequest request = new CourseReadRequest(1, 10, Direction.DESC.name(), orderColumn);
        Sort expected = Sort.by(Direction.DESC, OrderColumn.RECENT.getColumn());

        // when
        Pageable pageable = request.toPageable();

        // then
        assertThat(pageable.getSort()).isEqualTo(expected);
    }

    @DisplayName("orderType은 대소문자를 구분하지 않는다.")
    @Test
    void getSortIgnoreCase() {
        // given
        Direction direction = Direction.ASC;
        OrderColumn orderColumn = OrderColumn.MOST;
        CourseReadRequest request = new CourseReadRequest(1, 10, direction.name().toLowerCase(), orderColumn.name());
        Sort expected = Sort.by(direction, orderColumn.getColumn());

        // when
        Pageable pageable = request.toPageable();

        // then
        assertThat(pageable.getSort()).isEqualTo(expected);
    }
}
