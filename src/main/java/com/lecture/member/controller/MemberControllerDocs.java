package com.lecture.member.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lecture.member.service.dto.SignUpRequest;
import com.lecture.member.service.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member", description = "Member API")
public interface MemberControllerDocs {
    @Operation(summary = "회원 가입", description = "주어진 회원 정보로 회원 가입합니다.")
    @ApiResponses(value = {
            @ApiResponse(description = "회원가입 성공", responseCode = "201"),
            @ApiResponse(description = """
                    <발생 가능한 케이스>
                                        
                    (1) 필수 값이 누락되었을 때
                                        
                    (2) 이름 형식이 올바르지 않을 때: 1자 이상, 10자 이하
                                        
                    (3) 이메일 형식이 올바르지 않을 때
                                        
                    (4) 전화번호 형식이 올바르지 않을 때: 구분자(-)없이 번호만 입력
                                        
                    (5) 이미 존재하는 이메일/전화번호일 때
                                        
                    (6) 비밀번호 형식이 올바르지 않을 때: 최소 6자 이상, 10자 이하 / 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합
                                        
                    (7) 존재하지 않는 역할일 때: STUDENT / TEACHER
                    """,
                    responseCode = "400")
    })
    ResponseEntity<SignUpResponse> signUp(@Parameter(required = true) @Valid SignUpRequest signUpRequest);
}
