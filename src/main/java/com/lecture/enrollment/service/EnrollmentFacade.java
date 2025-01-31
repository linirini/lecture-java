package com.lecture.enrollment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponse;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollmentFacade {

    private final EnrollmentService enrollmentService;
    private final ApplicationContext applicationContext;

    @Async
    public CompletableFuture<EnrollmentResponses> enrollAll(EnrollmentRequest enrollmentRequest, Member member) {
        log.info("Executing enrollAll() in Thread: {}", Thread.currentThread().getName());
        TaskExecutor taskExecutor = applicationContext.getBean(TaskExecutor.class);
        log.info("Using TaskExecutor: {}", taskExecutor.getClass().getName());
        if (taskExecutor instanceof org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor) {
            var threadPoolTaskExecutor = (org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor) taskExecutor;
            log.info("TaskExecutor Core Pool Size: {}", threadPoolTaskExecutor.getCorePoolSize());
            log.info("TaskExecutor Max Pool Size: {}", threadPoolTaskExecutor.getMaxPoolSize());
            log.info("TaskExecutor Active Threads: {}", threadPoolTaskExecutor.getActiveCount());
        }

        List<EnrollmentResponse> enrollmentResponses = new ArrayList<>();
        for (Long courseId : enrollmentRequest.courseIds()) {
            enrollmentResponses.add(enroll(member, courseId));
        }

        log.info("Completed enrollAll() in Thread: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(new EnrollmentResponses(enrollmentResponses));
    }

    private EnrollmentResponse enroll(Member member, Long courseId) {
        try {
            enrollmentService.enroll(member, courseId);
            return EnrollmentResponse.succeed(courseId);
        } catch (Exception e) {
            log.error("Enrollment failed for course {} Reason: {}", courseId, e.getMessage());
            return EnrollmentResponse.fail(courseId, e.getMessage());
        }
    }
}
