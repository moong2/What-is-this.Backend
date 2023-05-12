package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.repository.AmendsRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AmendsService {

    private final MemberRepository memberRepository;
    private final AmendsRepository amendsRepository;

}
