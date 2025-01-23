package com.lecture.course.controller;

import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lecture.config.auth.LoginMember;
import com.lecture.course.service.CourseService;
import com.lecture.course.service.dto.CourseIdResponse;
import com.lecture.course.service.dto.CourseRequest;
import com.lecture.member.domain.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController implements CourseControllerDocs{

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseIdResponse> createCourse(@RequestBody @Valid CourseRequest courseRequest, @LoginMember Member member){
        CourseIdResponse courseIdResponse = courseService.createCourse(courseRequest, member);
        return ResponseEntity.created(URI.create("/courses/"+courseIdResponse.id())).body(courseIdResponse);
    }
}
