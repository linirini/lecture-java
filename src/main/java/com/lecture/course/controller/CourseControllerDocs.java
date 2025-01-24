package com.lecture.course.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseReadRequest;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.course.service.dto.CourseResponses;
import com.lecture.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Course", description = "Course API")
public interface CourseControllerDocs {

    @Operation(summary = "강의 등록", description = "주어진 강의 정보로 강의를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(description = "강의 등록 성공", responseCode = "201"),
            @ApiResponse(description = """
                    <발생 가능한 케이스>
                                        
                    (1) 필수 값이 누락되었을 때
                                        
                    (2) 강좌명 형식이 올바르지 않을 때: 1자 이상, 50자 이하
                                        
                    (3) 최대 수강 인원 형식이 올바르지 않을 때: 0명 이상
                                        
                    (4) 수강료 형식이 올바르지 않을 때: 0원 이상
                                        
                    (5) 이미 동일한 이름으로 강좌를 등록해놓았을 때
                    """,
                    responseCode = "400")
    })
    ResponseEntity<CourseIdResponse> createCourse(
            @Parameter(required = true) @Valid CourseRequest courseRequest,
            @Parameter(hidden = true) Member member
    );

    @Operation(summary = "강의 목록 조회", description = "주어진 정렬 조건과 페이징에 부합하는 강의 목록을 조회합니다.")
    @ApiResponse(description = "강의 조회 성공", responseCode = "200")
    ResponseEntity<CourseResponses> readAllCourses(CourseReadRequest courseReadRequest);
}
