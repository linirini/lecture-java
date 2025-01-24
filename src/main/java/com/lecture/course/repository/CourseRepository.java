package com.lecture.course.repository;

import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import com.lecture.course.domain.Course;
import com.lecture.course.domain.Title;
import com.lecture.member.domain.Member;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByMemberAndTitle(Member member, Title title);

    Slice<Course> findAllBy(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Course c where c.id = :id")
    Optional<Course> findByIdForUpdate(long id);
}
