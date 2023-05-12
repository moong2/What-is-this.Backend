package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.AmendsDto;
import com.Saojung.whatisthis.repository.AmendsRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
public class AmendsServiceTest {
    @InjectMocks
    private AmendsService amendsService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AmendsRepository amendsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        amendsService = new AmendsService(memberRepository, amendsRepository);
    }

    @Test
    @DisplayName("Update 테스트")
    void 업데이트_테스트() {
        //given
        Amends amends = Amends.builder()
                .idx(1L)
                .amends(null)
                .goal(0)
                .remain(0)
                .build();

        AmendsDto amendsDto = new AmendsDto(
                amends.getIdx(), "사과", 5, 5
        );

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .amends(amends)
                .build();

        BDDMockito.given(amendsRepository.findById(member.getAmends().getIdx())).willReturn(Optional.of(amends));
        BDDMockito.given(amendsRepository.save(any())).willReturn(amends);

        //when
        AmendsDto returnDto = amendsService.update(amendsDto);

        //then
        assertEquals(amendsDto.getIdx(), returnDto.getIdx());
        assertNotEquals(amendsDto.getAmends(), returnDto.getAmends());
        assertNotEquals(amendsDto.getGoal(), returnDto.getGoal());
        assertNotEquals(amendsDto.getRemain(), returnDto.getRemain());
    }

    @Test
    @DisplayName("Amends 가져오기")
    void Amends_가져오기() {
        //given
        Amends amends = Amends.builder()
                .idx(1L)
                .amends(null)
                .goal(0)
                .remain(0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .amends(amends)
                .build();

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(amendsRepository.findById(member.getAmends().getIdx())).willReturn(Optional.of(amends));

        //when
        AmendsDto amendsDto = amendsService.getAmends(member.getIdx());

        //then
        assertEquals(amendsDto.getIdx(), amends.getIdx());
        assertEquals(amendsDto.getAmends(), amends.getAmends());
        assertEquals(amends.getGoal(), amends.getGoal());
        assertEquals(amends.getRemain(), amends.getRemain());
    }
}
