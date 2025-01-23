package com.lecture.member.service;

import org.springframework.stereotype.Service;
import com.lecture.exception.LectureException;
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
        validateIfDuplicatedEmail(member);
        validateIfDuplicatedPhoneNumber(member);
        memberRepository.save(member);
        return new SignUpResponse(member);
    }

    private void validateIfDuplicatedEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new LectureException("이미 존재하는 이메일입니다. 다시 설정해주세요.");
        }
    }

    private void validateIfDuplicatedPhoneNumber(Member member) {
        if (memberRepository.existsByPhoneNumber(member.getPhoneNumber())) {
            throw new LectureException("이미 존재하는 전화번호입니다. 다시 설정해주세요.");
        }
    }
}
