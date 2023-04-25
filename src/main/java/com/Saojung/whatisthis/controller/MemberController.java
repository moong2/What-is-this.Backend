package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String signUp(@RequestBody MemberVo memberVo) {
        try {
            MemberDto memberDto = MemberDto.builder()
                    .userId(memberVo.getUserId())
                    .password(memberVo.getPassword())
                    .name(memberVo.getName())
                    .birth(memberVo.getBirth())
                    .parentPassword(memberVo.getParentPassword())
                    .amends(null)
                    .analysis(null)
                    .build();

            MemberDto resultDto = memberService.signUp(memberDto);

            return resultDto.getName() + "님의 회원가입이 완료되었습니다.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginVo loginVo) {
        try {
            MemberDto member = memberService.login(loginVo);

            MemberVo memberVo = new MemberVo(member.getIdx(), member.getUserId(), member.getPassword(), member.getName(), member.getBirth(), member.getParentPassword());

            return new ResponseEntity<>(memberVo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
