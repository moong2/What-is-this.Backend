package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.dto.AmendsDto;
import com.Saojung.whatisthis.service.AmendsService;
import com.Saojung.whatisthis.vo.AmendsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AmendsController {

    private final AmendsService amendsService;

    @GetMapping("/getAmends")
    public ResponseEntity getAmends(@RequestParam Long member_idx) {
        try {
            AmendsDto amends = amendsService.getAmends(member_idx);

            AmendsVo amendsVo = new AmendsVo(amends.getIdx(), amends.getAmends(), amends.getGoal(), amends.getRemain());

            return new ResponseEntity<>(amendsVo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
