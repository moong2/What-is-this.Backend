package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.repository.WordRepository;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.DuplicateMemberException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final WordRepository wordRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto signUp(MemberDto memberDto) {
        if (memberRepository.findByUserId(memberDto.getUserId()).orElse(null) != null)
            throw new DuplicateMemberException("이미 사용 중인 ID입니다.");

        try {
            Member member = Member.builder()
                    .userId(memberDto.getUserId())
                    .password(passwordEncoder.encode(memberDto.getPassword()))
                    .name(memberDto.getName())
                    .birth(memberDto.getBirth())
                    .parentPassword(passwordEncoder.encode(memberDto.getParentPassword()))
                    .analysis(memberDto.getAnalysis())
                    .amends(memberDto.getAmends())
                    .build();

            return MemberDto.from(memberRepository.save(member));
        } catch (Exception e) {
            throw new NullPointerException("필수 정보를 기입해주세요.");
        }
    }

    public List<MemberDto> getMembers() {
        ArrayList<MemberDto> members = new ArrayList<>();

        for (Member member : memberRepository.findAll()) {
            members.add(MemberDto.from(member));
        }

        return members;
    }

    public MemberDto update(MemberDto memberDto) {
        if (memberRepository.findById(memberDto.getIdx()).orElse(null) == null)
            throw new NoMemberException("존재하지 않는 회원입니다.");

        try {
            Member member = Member.builder()
                    .userId(memberDto.getUserId())
                    .password(passwordEncoder.encode(memberDto.getPassword()))
                    .name(memberDto.getName())
                    .birth(memberDto.getBirth())
                    .parentPassword(passwordEncoder.encode(memberDto.getParentPassword()))
                    .analysis(memberDto.getAnalysis())
                    .amends(memberDto.getAmends())
                    .build();

            return MemberDto.from(memberRepository.save(member));
        } catch (Exception e) {
            throw new NullPointerException("필수 정보를 기입해주세요.");
        }
    }

    public void withdraw(Long idx) {
        if (memberRepository.findById(idx).orElse(null) == null)
            throw new NoMemberException("존재하지 않는 회원입니다.");

        List<Word> words = wordRepository.findAllByMember_Idx(idx);
        for (Word word : words) {
            wordRepository.delete(word);
        }

        memberRepository.deleteById(idx);
    }

    public MemberDto login(LoginVo loginVo) {
        Optional<Member> byUserId = memberRepository.findByUserId(loginVo.getUserId());
        if (byUserId.orElse(null) == null || !passwordEncoder.matches(loginVo.getPassword(), byUserId.get().getPassword()))
            throw new NoMemberException("존재하지 않는 회원입니다.");

        return MemberDto.from(byUserId.get());
    }

    public MemberDto parentLogin(LoginVo loginVo) {
        Optional<Member> byUserId = memberRepository.findByUserId(loginVo.getUserId());
        if (byUserId.orElse(null) == null || !passwordEncoder.matches(loginVo.getParentPassword(), byUserId.get().getParentPassword()))
            throw new NoMemberException("존재하지 않는 회원입니다.");

        return MemberDto.from(byUserId.get());
    }

    public MemberDto findMember(Long idx) {
        Optional<Member> byId = memberRepository.findById(idx);

        if (byId.equals(Optional.empty()))
            throw new NoMemberException("회원이 존재하지 않습니다.");

        return MemberDto.from(byId.get());
    }
}
