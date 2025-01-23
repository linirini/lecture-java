package com.lecture.member.service;

import org.springframework.stereotype.Service;
import com.lecture.member.domain.Member;
import com.lecture.member.repository.MemberRepository;
import com.lecture.member.service.dto.SignUpRequest;
import com.lecture.member.service.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponse createMember(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        memberRepository.save(member);
        return new SignUpResponse(member);
    }
}
