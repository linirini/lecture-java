package com.lecture.fixture;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import com.lecture.enrollment.service.dto.EnrollmentRequest;

public class EnrollmentRequestFixture {
    public static EnrollmentRequest create(AtomicLong... courseIds) {
        return new EnrollmentRequest(Arrays.stream(courseIds).map(AtomicLong::get).toList());
    }
}
