package com.lecture.enrollment.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lecture.enrollment.service.dto.EnrollmentRequest;
import com.lecture.enrollment.service.dto.EnrollmentResponses;
import com.lecture.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Enrollment", description = "Enrollment API")
public interface EnrollmentControllerDocs {

    @Operation(summary = "수강 신청", description = "수강 신청을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(description = "수강신청 실행 성공", responseCode = "200"),
            @ApiResponse(description = """
                    <발생 가능한 케이스>
                                        
                    (1) 강좌 정보가 누락되었을 때
                                        
                    (2) 존재하지 않는 강좌일 때
                                        
                    (3) 이미 수강신청한 강좌일 때
                                        
                    (4) 최대 수강 인원이 초과했을 때
                    """,
                    responseCode = "400")
    })
    ResponseEntity<EnrollmentResponses> enroll(
            @Parameter(required = true) @Valid EnrollmentRequest enrollmentRequest,
            @Parameter(hidden = true) Member member
    );
}
