package com.lecture.course.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lecture.course.domain.Course;
import com.lecture.course.domain.Title;
import com.lecture.member.domain.Member;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByMemberAndTitle(Member member, Title title);

    Slice<Course> findAllBy(Pageable pageable);
}
