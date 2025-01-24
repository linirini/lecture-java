package com.lecture.enrollment.service.dto;

import java.util.List;

public record EnrollmentRequest(List<Long> courseIds) {
}
