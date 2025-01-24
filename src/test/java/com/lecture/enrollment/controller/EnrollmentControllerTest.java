package com.lecture.enrollment.controller;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.lecture.ControllerTest;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.exception.ExceptionResponse;
import com.lecture.fixture.MemberFixture;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnrollmentControllerTest extends ControllerTest {

    @DisplayName("필수 입력값을 누락하면 회원가입에 실패한다.")
    @Test
    void cannotEnrollIfIdsNull() throws Exception {
        // given
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(null);
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), "강좌 정보를 입력해주세요.");
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(MemberFixture.createTeacher()));

        // when & then
        mockMvc.perform(post("/enrollment")
                        .header(HttpHeaders.AUTHORIZATION, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }
}
