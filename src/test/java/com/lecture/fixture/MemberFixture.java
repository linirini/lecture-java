package com.lecture.fixture;

import com.lecture.member.domain.Email;
import com.lecture.member.domain.Member;
import com.lecture.member.domain.Name;
import com.lecture.member.domain.Password;
import com.lecture.member.domain.PhoneNumber;
import com.lecture.member.domain.Role;

public class MemberFixture {

    public static Member createTeacher() {
        return new Member(
                new Name("이름"),
                new Email("user@email.com"),
                new PhoneNumber("01012345678"),
                new Password("password1"),
                Role.TEACHER
        );
    }

    public static Member createTeacher(Email email, PhoneNumber phoneNumber) {
        return new Member(
                new Name("이름"),
                email,
                phoneNumber,
                new Password("password1"),
                Role.TEACHER
        );
    }
}
