package com.lecture.course.service.dto;

import java.util.Objects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import com.lecture.course.service.OrderColumn;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 목록 조회 시 정렬 및 페이징 쿼리 스트링 형식입니다. 유효하지 않은 값은 무시합니다.")
public record CourseReadRequest(
        @Schema(description = "페이지 번호를 입력하지 않으면 첫 페이지를 조회합니다.", example = "1")
        Integer pageNo,
        @Schema(description = "페이지 사이즈를 입력하지 않으면 20개씩 조회합니다.", example = "20")
        Integer pageSize,
        @Schema(description = "정렬 방식(대소문자 미구분, ASC/DESC)을 입력하지 않으면 내림차순으로 조회합니다.", example = "DESC")
        String orderType,
        @Schema(description = "정렬 기준(대소문자 미구분, RECENT/MOST/HIGHEST)을 입력하지 않으면 최근 등록순(RECENT)으로 조회합니다.", example = "RECENT")
        String orderColumn
) {

    private static final int DEFAULT_PAGE_NO = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final Direction DEFAULT_DIRECTION = Direction.DESC;
    private static final OrderColumn DEFAULT_ORDER_COLUMN = OrderColumn.RECENT;

    public Pageable toPageable() {
        return PageRequest.of(
                getPageNo(pageNo),
                getPageSize(pageSize),
                getDirection(orderType),
                getOrderColumn(orderColumn)
        );
    }

    private int getPageSize(Integer pageSize) {
        if (Objects.isNull(pageSize) || pageSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    private int getPageNo(Integer pageNo) {
        if (Objects.isNull(pageNo) || pageNo <= 0) {
            return DEFAULT_PAGE_NO;
        }
        return pageNo - 1;
    }

    private Direction getDirection(String orderType) {
        try {
            return Direction.fromString(orderType);
        } catch (IllegalArgumentException e) {
            return DEFAULT_DIRECTION;
        }
    }

    private String getOrderColumn(String orderColumn) {
        try {
            return OrderColumn.findByCondition(orderColumn).getColumn();
        } catch (IllegalArgumentException e) {
            return DEFAULT_ORDER_COLUMN.getColumn();
        }
    }
}
