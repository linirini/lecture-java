package com.lecture.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lecture.ServiceSliceTest;
import com.lecture.fixture.SignUpRequestFixture;
import com.lecture.member.repository.MemberRepository;
import com.lecture.member.service.dto.SignUpRequest;
import com.lecture.member.service.dto.SignUpResponse;

import static org.assertj.core.api.Assertions.assertThat;
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
        SignUpRequest signUpRequest = SignUpRequestFixture.create();

        //when
        SignUpResponse signUpResponse = memberService.createMember(signUpRequest);

        //then
        assertAll(
                () -> assertThat(memberRepository.findAll()).hasSize(1),
                () -> assertThat(memberRepository.findById(signUpResponse.id())).isPresent()
        );
    }
}
