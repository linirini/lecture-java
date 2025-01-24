package com.lecture.course.service.dto;

import java.util.Objects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import com.lecture.course.service.OrderColumn;

public record CourseReadRequest(
        Integer pageNo,
        Integer pageSize,
        String orderType,
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
