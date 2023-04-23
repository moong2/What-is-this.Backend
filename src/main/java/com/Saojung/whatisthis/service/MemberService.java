package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.LoginDto;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.DuplicateMemberException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto signUp(MemberDto memberDto) {
        if (memberRepository.getReferenceById(memberDto.getId()) != null)
            throw new DuplicateMemberException("이미 사용 중인 ID입니다.");

        Member member = Member.builder()
                .id(memberDto.getId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .birth(memberDto.getBirth())
                .parentPassword(passwordEncoder.encode(memberDto.getParentPassword()))
                .analysis(memberDto.getAnalysis())
                .amends(memberDto.getAmends())
                .build();

        return MemberDto.from(memberRepository.save(member));
    }

    public List<MemberDto> getMembers() {
        ArrayList<MemberDto> members = new ArrayList<>();

        for (Member member : memberRepository.findAll()) {
            members.add(MemberDto.from(member));
        }

        return members;
    }

    public MemberDto update(MemberDto memberDto) {
        if (memberRepository.getReferenceById(String.valueOf(memberDto.getIdx())) == null)
            throw new NoMemberException("존재하지 않는 회원입니다.");

        memberRepository.update(memberDto.getId(), passwordEncoder.encode(memberDto.getPassword()), memberDto.getName(), memberDto.getBirth(), passwordEncoder.encode(memberDto.getParentPassword()), memberDto.getIdx());

        return MemberDto.from(memberRepository.getReferenceById(String.valueOf(memberDto.getIdx())));
    }

    public void withdraw(MemberDto memberDto) {
        if (memberRepository.getReferenceById(String.valueOf(memberDto.getIdx())) == null)
            throw new NoMemberException("존재하지 않는 회원입니다.");

        memberRepository.deleteById(String.valueOf(memberDto.getIdx()));
    }

    public MemberDto login(LoginDto loginDto) {
        Optional<Member> login_member = memberRepository.findByIdAndPassword(loginDto.getId(), loginDto.getPassword());

        if (login_member.equals(Optional.empty()))
            throw new NoMemberException("존재하지 않는 회원입니다");

        return MemberDto.from(login_member.get());
    }

    public MemberDto parentLogin(LoginDto loginDto) {
        Optional<Member> login_member = memberRepository.findByIdAndParentPassword(loginDto.getId(), loginDto.getParentPassword());

        if (login_member.equals(Optional.empty()))
            throw new NoMemberException("존재하지 않는 회원입니다.");

        return MemberDto.from(login_member.get());
    }
}
