# 이건 뭐지? [What-is-this?]

<div align="center">
<img width="300" src="https://github.com/Sao-jung-listens-well/WIT/blob/main/Assets/Resources/pumpkin_gold.PNG?raw=true">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSao-jung-listens-well%2FBackend.git&count_bg=%23F8F981&title_bg=%23CBA0EB&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
</div>

> **세종대학교 2023-1 캡스톤디자인및설계 '말잘듣는 사오정'**
<br>**개발 기간 : 2023.04 ~ 2023.05**

## 배포 주소
> **프론트** : https://github.com/Sao-jung-listens-well/WIT <br>
> **백엔드** : https://github.com/Sao-jung-listens-well/Backend.git <br>

## 개발팀
|                                       이승재                                        |                                       한보은                                        |                                       김한슬                                       |                                       박성하                                       |                                                                               
|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|
| <img width="160px" src="https://avatars.githubusercontent.com/u/81508501?v=4" /> | <img width="160px" src="https://avatars.githubusercontent.com/u/81304917?v=4" /> | <img width="160px" src="https://avatars.githubusercontent.com/u/67732600?v=4"/> | <img width="160px" src="https://avatars.githubusercontent.com/u/67732143?v=4"/> |
|                      [@fghy788](https://github.com/fghy788)                      |                     [@Boeunhan](https://github.com/BoeunHan)                     |                      [@slcloe](https://github.com/slcloe)                       |                      [@moong2](https://github.com/moong2)                       |
|                                  프론트엔드 - AR 담당                                   |                               프론트엔드 - UI / 음성인식 담당                               |                                 프론트엔드 - 사물인식 담당                                 |                                     백엔드 담당                                      |

## 프로젝트 소개
사물인식, 음성인식, AR을 통해 주변과 상호작용할 수 있는 유아 한글 교육 어플리케이션입니다. <br>
현재, 한글 교육 시장에는 전문가가 강조하는 한글 교육 순서인 통글자 -> 자모음 순서를 지키는 어플리케이션이 많지 않을 뿐더러 주변과 상호작용을 할 수 있는 어플리케이션은 존재하지 않아 아쉬움이 있었습니다. <br>
그러한 이유로 사물인식 기능을 적용해 주변 사물을 통글자로 배우고, 말하기 연습을 히거 AR로 숨겨진 자모음을 찾아 배운 통글자를 조합하는 어플리케이션을 만들게 되었습니다. <br>
또한, 영어 기능을 넣어 한글을 배우고 싶은 외국인도 사용할 수 있도록 제작하였습니다.

This is a preschool Korean education application that allows interaction with the surroundings through object recognition, voice recognition, and AR. <br>
urrently, in the Korean education market, there are not many applications that follow the recommended Korean education sequence of teaching integrated letters first, followed by individual letters. <br>
For these reason, there is a lack of applications that allow interaction with the surroundings. Due to these reasons, we felt a sense of regret. Therefore, we have developed an application that incorporates object recognition to learn integrated letters through surrounding objects and allows practicing speaking by using AR to find hidden individual letters and combine them to form the learned integrated letters. <br>
Moreover, we have included an English feature to make the application usable for foreigners who are interested in learning Korean.

## 제공되는 기능
1. **부모페이지 기능** - 학습에 대한 분석값, 아이의 성취감을 위한 보상 기능 제공
2. **통글자 학습** - 사물인식을 통해 주변 사물을 통글자로 학습 가능
3. **음성 학습** - 음성 인식을 통해 통글자를 학습한 글자를 음성 학습할 수 있음
4. **자모음 학습** - AR을 통해 집안 곳곳에 숨겨진 자모음을 찾아 조합하며 학습할 수 있음
5. **난이도 시스템** - 일정 기준을 통과하면 음성, 자모음 학습이 해금되는 형식
6. **영어 제공** - 한글을 배우고 싶어하는 외국인을 위해 영어 번역 시스템 제공
<br>
<br>

**Our application supports the following component.**
1. **Parent Page** - This feature provides analysis of the child's learning progress and offers a reward system to enhance their sense of achievement.
2. **Integrated Letter Learning** - Through object recognition, children can learn integrated letters by identifying objects in their surroundings.
3. **Voice Learning** - Using voice recognition, children can practice the pronunciation of the integrated letters they have learned.
4. **Individual Letter Learning** - Through AR, children can search for hidden individual letters throughout the house and learn by combining them.
5. **Difficulty System** - The application includes a difficulty system where the unlocking of voice and individual letter learning is based on meeting certain criteria.
6. **English Support** - The application provides an English translation system for foreigners who are interested in learning Korean.

## 개발 환경

### Environment
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)
![iTerm2](https://img.shields.io/badge/iTerm2-000000?style=for-the-badge&logo=iTerm2&logoColor=white)

### Development
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white)
![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=Amazon%20RDS&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white)
![NGINX](https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white)

### Communication
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white)


<h1>ERD</h1>
<img width="80%" src="https://user-images.githubusercontent.com/67732143/237047836-4ace6ae8-89dc-48db-8635-1912a91f3e6e.png"/>

<h1>TDD</h1>
<h3>유저 API</h3>
<div>
1. 회원가입

- 속성 NULL 여부 테스트
- 패스워드 암호화 (only service test)
- 부모 패스워드 암호화 (only service test)
- 중복 아이디 검사 (only service test)
- 회원 정보 받고 결과 반환 (controller test)
</div>
<div>
2. 로그인

- 아이디, 패스워드 조회
- 아이디, 패스워드 동일 여부 검사 (only repository test)
- 로그인 성공 / 실패 (only service test)
- 부모 패스워드 동일 여부 검사 (only repository test)
- 부모 페이지 로그인 성공 / 실패 (only service test)
- 로그인 정보 받고 정보 반환 (controller test)
- 부모 패스워드 관련 정보 받고 Amends, Analysis 반환 (controller test)
</div>
<div>
3. 회원 정보 조회

- 조회
- 회원 정보 변경
- 삭제
- 빈 db 조회 예외 핸들링
- 변경된 정보 받고 결과 반환 (controller test)
- 삭제할 정보 받고 결과 반환 (controller test)
</div>

<h3>단어 학습 API</h3>
<div>
1. 단어 학습

- 단어 학습(추가)
- 학습 단어 업데이트 (crud 전용)
</div>
<div>
2. 학습 단어 조회

- 날짜 정렬 확인
- 제공 난이도 정렬 확인
- 조회
- 빈 db 조회 예외 핸들링
</div>
<div>
3. 학습 단어 제거

- cascade 테스트 (service test)
</div>

<h3>학습 진척도 API</h3>
<div>
1. 분석 데이터 추가

- null 확인
</div>
<div>
2. 분석 데이터 조회

- 빈 db 조회 예외 핸들링
- 조회
</div>
<div>
3. 데이터 분석

- 성공률 분석
- word 테이블 빈 db 조회 예외 핸들링
- 성공률에 따른 레벨 설정
</div>

<h3>보상 API</h3>
<div>
1. 보상 정보 추가

- null 확인
</div>
<div>
2. 보상 정보 조회

- 빈 db 조회 예외 핸들링
- 조회
</div>
<div>
3. 보상 수여 계산

- 잔여 일수 계산
</div>

<h2><strike>클라우드 연결 (AWS)</strike></h2>
<h2><strike>CI/CD (jenkins)</strike></h2>
<h2><strike>ec2에 jenkins 올리기</strike></h2>
<h2><strike>무중단 배포 (NGINX)</strike></h2>
