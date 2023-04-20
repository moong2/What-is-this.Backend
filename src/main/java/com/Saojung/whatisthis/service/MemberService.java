package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.DuplicateMemberException;
import com.Saojung.whatisthis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto signUp(MemberDto memberDto) {
        if (memberRepository.findById(memberDto.getId()) == null)
            throw new DuplicateMemberException("이미 사용 중인 ID입니다.");

        Member member = Member.builder()
                .id(memberDto.getId())
                .password(memberDto.getPassword())
                .name(memberDto.getName())
                .birth(memberDto.getBirth())
                .analysis(memberDto.getAnalysis())
                .amends(memberDto.getAmends())
                .build();

        return MemberDto.from(memberRepository.save(member));
    }
}
