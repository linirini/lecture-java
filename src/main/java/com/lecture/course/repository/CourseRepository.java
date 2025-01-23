package com.lecture.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lecture.course.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
