package com.lecture.fixture;

import com.lecture.member.service.dto.SignUpRequest;

public class SignUpRequestFixture {

    public static SignUpRequest createStudent() {
        return new SignUpRequest("name", "user@email.com", "01012345678", "password1", "STUDENT");
    }

    public static SignUpRequest createStudent(String email, String phoneNumber) {
        return new SignUpRequest("name", email, phoneNumber, "password1", "STUDENT");
    }

    public static SignUpRequest createTeacher() {
        return new SignUpRequest("name", "user@email.com", "01012345678", "password1", "TEACHER");
    }

    public static SignUpRequest createTeacher(String email, String phoneNumber) {
        return new SignUpRequest("name", email, phoneNumber, "password1", "TEACHER");
    }

    public static SignUpRequest createWithEmail(String email) {
        return new SignUpRequest("name", email, "01012345678", "password1", "STUDENT");
    }

    public static SignUpRequest createWithPhoneNumber(String phoneNumber) {
        return new SignUpRequest("name", "user@email.com", phoneNumber, "password1", "STUDENT");
    }
}
