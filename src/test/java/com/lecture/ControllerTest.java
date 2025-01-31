package com.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecture.course.service.CourseService;
import com.lecture.enrollment.service.EnrollmentFacade;
import com.lecture.member.repository.MemberRepository;
import com.lecture.member.service.MemberService;

@WebMvcTest
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected MemberService memberService;
    @MockBean
    protected MemberRepository memberRepository;
    @MockBean
    protected CourseService courseService;
    @MockBean
    protected EnrollmentFacade enrollmentFacade;
}
