package com.lecture.course.service.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import com.lecture.course.service.OrderColumn;

public record CourseReadRequest(
        int pageNo,
        int pageSize,
        String orderColumn,
        String orderType
) {

    public Pageable toPageable() {
        return PageRequest.of(mapToPhysicalPageNo(pageNo), pageSize, Direction.fromString(orderType), OrderColumn.findByCondition(orderColumn));
    }

    private int mapToPhysicalPageNo(int pageNo) {
        if (pageNo <= 0) {
            return 0;
        }
        return pageNo - 1;
    }
}
