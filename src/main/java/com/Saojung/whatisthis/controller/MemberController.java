package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.CannotJoinException;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody MemberVo memberVo) {
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

            return new ResponseEntity<>(resultDto.getName() + "님의 회원가입이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @PostMapping("/withdraw")
    public ResponseEntity withDraw(@RequestParam Long idx) {
        try {
            memberService.withdraw(idx);

            return new ResponseEntity<>("회원탈퇴가 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
