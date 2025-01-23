package com.lecture.course.domain;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Price {

    private static final long MIN_PRICE = 0;
    private static final String INVALID_PRICE_MESSAGE = String.format("수강료는 %d원 이상으로 설정해주세요.", MIN_PRICE);

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    public Price(long price) {
        validate(price);
        this.price = BigDecimal.valueOf(price);
    }

    private void validate(long price) {
        if (price < MIN_PRICE) {
            throw new LectureException(INVALID_PRICE_MESSAGE);
        }
    }
}
