package com.lecture.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.course.repository.CourseRepository;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.fixture.CourseRequestFixture;
import com.lecture.fixture.MemberFixture;
import com.lecture.member.domain.Member;
import com.lecture.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CourseServiceTest extends ServiceSliceTest {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("주어진 강의 정보로 새로운 강의를 생성한다.")
    @Test
    void createCourse() {
        // given
        Member member = memberRepository.save(MemberFixture.createTeacher());
        CourseRequest courseRequest = CourseRequestFixture.create();

        //when
        CourseIdResponse courseIdResponse = courseService.createCourse(courseRequest, member);

        //then
        assertAll(
                () -> assertThat(courseRepository.findAll()).hasSize(1),
                () -> assertThat(courseRepository.findById(courseIdResponse.id())).isPresent()
        );
    }
}
