package com.lecture.course.controller;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.lecture.ControllerTest;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.exception.ExceptionResponse;
import com.lecture.fixture.MemberFixture;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest extends ControllerTest {

    static Stream<Arguments> invalidCourseRequestProvider() {
        return Stream.of(
                Arguments.of(new CourseRequest(null, 10L, 200000L), "강좌명을 입력해주세요."),
                Arguments.of(new CourseRequest("  ", 10L, 200000L), "강좌명을 입력해주세요."),
                Arguments.of(new CourseRequest("강좌명", null, 200000L), "최대 수강 인원을 입력해주세요."),
                Arguments.of(new CourseRequest("강좌명", 10L, null), "수강료를 입력해주세요.")
        );
    }

    @DisplayName("필수 입력값을 누락하면 강좌 등록에 실패한다.")
    @ParameterizedTest
    @MethodSource("invalidCourseRequestProvider")
    void cannotCreateCourseWithoutNecessary(CourseRequest courseRequest, String expectedMessage) throws Exception {
        // given
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), expectedMessage);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MemberFixture.createTeacher()));

        // when & then
        mockMvc.perform(post("/courses")
                        .header(HttpHeaders.AUTHORIZATION, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }
}
