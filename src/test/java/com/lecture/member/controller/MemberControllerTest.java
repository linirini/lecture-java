package com.lecture.member.controller;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.lecture.ControllerTest;
import com.lecture.exception.ExceptionResponse;
import com.lecture.member.service.dto.SignUpRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTest {

    static Stream<Arguments> invalidSignUpRequestProvider() {
        return Stream.of(
                Arguments.of(new SignUpRequest(null, "user@email.com", "01012345678", "password1", "STUDENT"), "이름을 입력해주세요."),
                Arguments.of(new SignUpRequest("   ", "user@email.com", "01012345678", "password1", "STUDENT"), "이름을 입력해주세요."),
                Arguments.of(new SignUpRequest("name", null, "01012345678", "password1", "STUDENT"), "이메일을 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "   ", "01012345678", "password1", "STUDENT"), "이메일을 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", null, "password1", "STUDENT"), "전화번호를 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", "   ", "password1", "STUDENT"), "전화번호를 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", "01012345678", null, "STUDENT"), "비밀번호를 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", "01012345678", "   ", "STUDENT"), "비밀번호를 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", "01012345678", "password1", null), "역할을 입력해주세요."),
                Arguments.of(new SignUpRequest("name", "user@email.com", "01012345678", "password1", "   "), "역할을 입력해주세요.")
        );
    }

    @DisplayName("필수 입력값을 누락하면 회원가입에 실패한다.")
    @ParameterizedTest
    @MethodSource("invalidSignUpRequestProvider")
    void cannotSignUpWithoutNecessary(SignUpRequest signUpRequest, String expectedMessage) throws Exception {
        // given
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), expectedMessage);

        // when & then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(exceptionResponse)));
    }
}
