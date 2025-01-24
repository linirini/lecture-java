package com.lecture.enrollment.controller;

import java.util.concurrent.CompletableFuture;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lecture.config.auth.LoginMember;
import com.lecture.enrollment.service.EnrollmentService;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponses> enroll(@Valid @RequestBody EnrollmentRequest enrollmentRequest, @LoginMember Member member){
        CompletableFuture<EnrollmentResponses> futureResponses = enrollmentService.enrollAll(enrollmentRequest, member);
        return ResponseEntity.ok(futureResponses.join());
    }
}
