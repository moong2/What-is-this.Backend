# Backend
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
<h2>무중단 배포 (NGINX)</h2>
