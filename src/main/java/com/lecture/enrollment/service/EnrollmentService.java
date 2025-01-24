package com.lecture.enrollment.service;

import org.springframework.stereotype.Service;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    public EnrollmentResponses enroll(EnrollmentRequest enrollmentRequest, Member member) {
        return null;
    }
}
