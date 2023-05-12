package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Amends;
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
}
