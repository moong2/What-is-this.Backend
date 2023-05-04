package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.dto.WordDto;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.service.WordService;
import com.Saojung.whatisthis.vo.WordVo;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;
    private final MemberService memberService;

    @PostMapping("/studyWord")
    public ResponseEntity studyWord(@RequestParam Long member_idx, @RequestBody WordVo wordVo) {
        try {
            MemberDto returnDto = memberService.findMember(member_idx);
            Member member = Member.builder()
                    .idx(returnDto.getIdx())
                    .userId(returnDto.getUserId())
                    .password(returnDto.getPassword())
                    .name(returnDto.getName())
                    .birth(returnDto.getBirth())
                    .parentPassword(returnDto.getParentPassword())
                    .amends(returnDto.getAmends())
                    .analysis(returnDto.getAnalysis())
                    .build();

            WordDto wordDto = WordDto.builder()
                    .word(wordVo.getWord())
                    .level(wordVo.getLevel())
                    .successLevel(wordVo.getSuccessLevel())
                    .date(wordVo.getDate())
                    .member(member)
                    .build();

            wordService.create(wordDto);

            return new ResponseEntity<>("단어 학습이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getWords")
    public ResponseEntity getWords(@RequestParam Long member_idx) {
        try {
            List<WordDto> words = wordService.getWordsByDate(member_idx);

            List<WordVo> wordVos = new ArrayList<>();
            for (WordDto word : words) {
                WordVo wordVo = new WordVo(word.getIdx(), word.getWord(), word.getLevel(), word.getSuccessLevel(), word.getDate());
                wordVos.add(wordVo);
            }

            JSONObject result = new JSONObject();
            result.put("words", wordVos);

            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
