package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.service.AnalysisService;
import com.Saojung.whatisthis.vo.AnalysisVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping("/getAnalysis")
    public ResponseEntity getAnalysis(@RequestParam Long member_idx) {
        try {
            AnalysisDto analysisDto = analysisService.calculate(member_idx);

            AnalysisVo analysisVo = new AnalysisVo(analysisDto.getIdx(), analysisDto.getCount(), analysisDto.getLevel(), analysisDto.getSuccessRate1(), analysisDto.getSuccessRate2(), analysisDto.getSuccessRate3());

            return new ResponseEntity<>(analysisVo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAnalysisDate")
    public ResponseEntity getAnalysis(@RequestParam Long member_idx, LocalDateTime date) {
        try {
            AnalysisDto analysisDto = analysisService.calculate(member_idx, date);

            AnalysisVo analysisVo = new AnalysisVo(analysisDto.getIdx(), analysisDto.getCount(), analysisDto.getLevel(), analysisDto.getSuccessRate1(), analysisDto.getSuccessRate2(), analysisDto.getSuccessRate3());

            return new ResponseEntity<>(analysisVo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLevel")
    public ResponseEntity getLevel(@RequestParam Long member_idx) {
        try {
            Integer level = analysisService.getLevel(member_idx);

            return new ResponseEntity<>(level, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
