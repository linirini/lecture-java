package com.lecture.fixture;

import com.lecture.member.domain.Email;
import com.lecture.member.domain.Member;
import com.lecture.member.domain.Name;
import com.lecture.member.domain.Password;
import com.lecture.member.domain.PhoneNumber;
import com.lecture.member.domain.Role;

public class MemberFixture {

    public static Member create(Email email, PhoneNumber phoneNumber) {
        return new Member(
                new Name("이름"),
                email,
                phoneNumber,
                new Password("password1"),
                Role.TEACHER
        );
    }
}
