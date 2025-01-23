package com.lecture.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.exception.LectureException;
import com.lecture.fixture.MemberFixture;
import com.lecture.fixture.SignUpRequestFixture;
import com.lecture.member.domain.Email;
import com.lecture.member.domain.PhoneNumber;
import com.lecture.member.repository.MemberRepository;
import com.lecture.member.service.dto.SignUpRequest;
import com.lecture.member.service.dto.SignUpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberServiceTest extends ServiceSliceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("주어진 회원 정보로 새로운 회원을 생성한다.")
    @Test
    void createMember() {
        // given
        SignUpRequest signUpRequest = SignUpRequestFixture.createStudent();

        //when
        SignUpResponse signUpResponse = memberService.createMember(signUpRequest);

        //then
        assertAll(
                () -> assertThat(memberRepository.findAll()).hasSize(1),
                () -> assertThat(memberRepository.findById(signUpResponse.id())).isPresent()
        );
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성할 수 없다.")
    @Test
    void cannotCreateMemberByDuplicatedEmail() {
        // given
        Email email = new Email("user@email.com");
        PhoneNumber phoneNumber = new PhoneNumber("01087654321");
        memberRepository.save(MemberFixture.create(email, phoneNumber));
        SignUpRequest signUpRequest = SignUpRequestFixture.createWithEmail(email.getEmail());

        //when
        assertThatThrownBy(() -> memberService.createMember(signUpRequest))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 존재하는 이메일입니다. 다시 설정해주세요.");
    }

    @DisplayName("이미 존재하는 전화번호로 회원을 생성할 수 없다.")
    @Test
    void cannotCreateMemberByDuplicatedPhoneNumber() {
        // given
        Email email = new Email("other@email.com");
        PhoneNumber phoneNumber = new PhoneNumber("01012345678");
        memberRepository.save(MemberFixture.create(email, phoneNumber));
        SignUpRequest signUpRequest = SignUpRequestFixture.createWithPhoneNumber(phoneNumber.getPhoneNumber());

        //when
        assertThatThrownBy(() -> memberService.createMember(signUpRequest))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 존재하는 전화번호입니다. 다시 설정해주세요.");
    }
}
