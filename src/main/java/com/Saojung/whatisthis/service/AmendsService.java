package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.dto.AmendsDto;
import com.Saojung.whatisthis.exception.LevelException;
import com.Saojung.whatisthis.exception.NoAmendsException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.repository.AmendsRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AmendsService {

    private final MemberRepository memberRepository;
    private final AmendsRepository amendsRepository;

    public AmendsDto update(AmendsDto amendsDto) {
        if (amendsRepository.findById(amendsDto.getIdx()).orElse(null) == null)
            throw new NoAmendsException("보상 정보가 존재하지 않습니다.");

        Amends amends;
        try {
            amends = Amends.builder()
                    .idx(amendsDto.getIdx())
                    .amends(amendsDto.getAmends())
                    .goal(amendsDto.getGoal())
                    .remain(amendsDto.getRemain())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (amends.getGoal() < amends.getRemain() ||
            amends.getGoal() < 0 || amends.getRemain() < 0)
            throw new LevelException("비정상적인 값입니다.");

        return AmendsDto.from(amendsRepository.save(amends));
    }

    public AmendsDto getAmends (Long member_idx) {
        if (memberRepository.findById(member_idx).orElse(null) == null)
            throw new NoMemberException("회원이 존재하지 않습니다.");

        Optional<Amends> amends = amendsRepository.findById(memberRepository.findById(member_idx).get().getAmends().getIdx());
        if (amends.equals(Optional.empty()))
            throw new NoAmendsException("보상 정보가 존재하지 않습니다.");

        return AmendsDto.from(amends.get());
    }
}
