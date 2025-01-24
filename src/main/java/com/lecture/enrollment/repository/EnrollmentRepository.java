package com.lecture.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lecture.enrollment.domain.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByMemberIdAndCourseId(long memberId, long courseId);
}
