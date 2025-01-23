package com.lecture.course.domain;

import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Capacity {

    private static final long MIN_CAPACITY = 1;
    private static final String INVALID_CAPACITY_MESSAGE = String.format("최대 수강 인원은 %d명 이상으로 설정해주세요.", MIN_CAPACITY);

    private long capacity;

    public Capacity(long capacity) {
        validate(capacity);
        this.capacity = capacity;
    }

    private void validate(long capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new LectureException(INVALID_CAPACITY_MESSAGE);
        }
    }
}
