package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WordService {
    private final WordRepository wordRepository;
}
