package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.CannotJoinException;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.vo.AmendsVo;
import com.Saojung.whatisthis.vo.AnalysisVo;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

    @PostMapping("/plogin")
    public ResponseEntity plogin(@RequestBody LoginVo loginVo) {
        try {
            MemberDto member = memberService.parentLogin(loginVo);

            Analysis analysis = member.getAnalysis();
            AnalysisVo analysisVo = new AnalysisVo(analysis.getIdx(), analysis.getCount(), analysis.getLevel(), analysis.getSuccessRate1(), analysis.getSuccessRate2(), analysis.getSuccessRate3());
            ArrayList<AnalysisVo> analysisVos = new ArrayList<>();
            analysisVos.add(analysisVo);

            Amends amends = member.getAmends();
            AmendsVo amendsVo = new AmendsVo(amends.getIdx(), amends.getAmends(), amends.getGoal(), amends.getRemain());
            ArrayList<AmendsVo> amendsVos = new ArrayList<>();
            amendsVos.add(amendsVo);

            JSONObject result = new JSONObject();
            result.put("analysis", analysisVos);
            result.put("amends", amendsVos);

            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
