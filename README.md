## 프로젝트명 : 미리내

__https://j5a506.p.ssafy.io__

<br/>

# 📜목차

1. [프로젝트 개요](#프로젝트-개요)
2. [프로젝트 명세](#프로젝트-명세)
   - [팀원 및 역할](#팀원-및-역할)
   - [IDE 및 개발환경](#ide-및-개발환경)
   - [개발 환경 조성](#개발-환경-조성)
3. [서비스 제공 기능](#서비스-제공-기능)
4. [기술 상세 명세](#기술-상세-명세)
   - [Front End](#front-end)
   - [Back End](#back-end)
   - [Block Chain](#block-chain)

---

<br/>

# 🎯프로젝트 개요
 - __기부가 전달되는 과정에 대한 불신을 블록체인 기술을 통해 완화함으로써 차세대 기부 플랫폼을 제시__

---

<br/>

# 📋프로젝트 명세

## 👨‍👩‍👦‍👦팀원 및 역할

김호석(팀장)  - Blockchain  
노진혁        - Front-End  
신한규        - Back-End  
이예은        - Back-End  
이희정        - Blockchain  

<br/>

## 🔨IDE 및 개발환경

### Server OS
  - Ubuntu 20.04 LTS

### IDE
  - __Visual Studio Code__
  - __IntelliJ pro__
  - __Remix__ [Remix IDE](https://remix.ethereum.org/)  
![화면_캡처_2021-10-08_025108](/uploads/274700d5dcb4d2ce2d6c52db9bd0406e/화면_캡처_2021-10-08_025108.jpg)  

### Design Resources
  - __Tailwind__  
 ![hello-tailwind-css-thumbnail](/uploads/4de8c63564e8a1eb3930238025b94b7d/hello-tailwind-css-thumbnail.jpg)

<br/>

## 💻개발 환경 조성
- 백엔드 서버와 블록체인 노드를 구성하는 서버를 별도로 운용  
__docker images__  
  - Back-End Server
    - nginx
    - openjdk:11
    - mysql
    - node
    - Jenkins
  - Block Chain Server
    - ethereum/client-go

### 개발 환경 조성 순서
__백엔드 서버__
```
  1. AWS Ubuntu 20.04 LTS (쾌적환 환경을 위해 2개 운용 권장)
  2. docker 설치 및 위의 이미지 설치
  3. docker network 설정 - 백엔드(java), 프론트엔드(Nginx), DB(Mysql)
  4. mysql 설치 및 유저,DB 생성 [application.properties](/backend/src/resources/application.properties)
  5. certbot을 통한 https 설정 -> etc/letsencrypt
  6. Jenkins 설치 및 바인딩 [docker-compose](/exec/docker-compose.yml)
  7. Jenkins와 Gitlab연동 [파이프라인](/Jenkinsfile)
  8. Jenkins build에 따른 자동배포  
```

__블록체인 서버__
```
  1. AWS Ubuntu 20.04 LTS
  2. docker 설치 및 위의 이미지 설치
  3. docker network 설정 - Node1, Node2, ...
  4. genesis.json 생성 및 init [블록 생성](/smart-contract/make_block.sh)
  5. Geth를 통해 Node실행 - 옵션 필요에 따라 변경 [Geth 실행](/smart-contract/make_network.sh)
  6. Front-End에 Node주소 적용 -> const ENDPOINT [makeWallet](/frontend/src/components/user/UserContent.vue)  
```

__스마트 컨트랙트 배포__
```
  1. Remix IDE에 solidity파일 컴파일 -> ABI 코드
  2. Web3 Provider에 Node주소 입력 후 배포 -> 배포 주소
  3. Back-End에 Smart-Contract주소 적용 -> contract [EtereumUtil](/backend/src/main/java/com/a506/mirinae/util/EtereumUtil.java)  
```

---

<br/>

# 서비스 제공 기능

## 페이지별 기능 명세

서비스 소개 PPT : 미리내_최종.pptx 참조

<br/>

## 사용자별 제공 기능

### 서비스 이용자
 - 회원가입 (카카오 로그인)
 - 계좌 개설
 - 펀딩 개설 (관리자 승인 필요)
 - 펀딩 참여 (기부)
 - 그 외 정보 조회

### 서비스 관리자
 - 펀딩 승인/거부

---

<br/>

# 🔍기술 상세 명세

## 💎Front End
Tool : __Vue.js 3__

### Tailwind
[Tailwind docs](https://tailwindcss.com/docs)
- npm:@tailwindcss/postcss7-compat@^2.2.14
- [tailwind.config](/frontend/tailwind.config.js)  
> __Tailwind 특징__  
    Utility First를 지향하는 CSS 프레임워크  
    className 속성에 작성하여 길어질 수 있으나 디자인 일관성 유지 가능  
    반응형 유틸리티 클래스를 활용하여 PC, 앱 여러 환경에서 작동하는 페이지 구성이 쉬움  

<br/>

### OAuth2 - KAKAO인증
[OAuth2 docs](https://oauth.net/2/)  
[KakaoLogin docs](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)  
__Provider(KAKAO)로부터 사용자의 정보를 요청하여 인증__
- KAKAO 로그인 로직 [OauthCallback](/frontend/src/components/oauth/OauthCallback.vue)
```
store.dispatch('root/getKakaoToken', { code: code }) ...
store.dispatch('root/getKakaoInfo', { access_token: access_token }) ...
store.dispatch('root/login', { email:email, nickname:nickname, oauthType:oauthType }) ...
localStorage.setItem('jwt',result.data.jwt)
```
__Kakao Token을 통해 인증 후 백엔드 서버에서 JWT 발급__
- LOGIC에 사용되는 함수 및 변수 - [Vue.js store](frontend/src/actions.js)
  - const kakaoLogin - API키 및 주소
  - getKakaoCode
  - getKakaoToken
  - checkKakaoToken
  - getKakaoInfo
  - KakaoLogout

<br/>

### 네비게이터
[MainHeader](/frontend/src/components/main/MainHeader.vue)
  - 로그인 상태 확인

### views
  - WelcomPage : WelcomContent
  - MainPage : Main, User, Funding(detail, create, confirm)
  - ErrorPage : 

### 웰컴페이지
[WelcomPage](/frontend/src/components/welcome/WelcomeContent.vue)  
  - 카카오 로그인시 리다이렉팅 - redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao"

### 메인페이지
[MainPage](/frontend/src/components/main/MainContent.vue)  
__포함 컴포넌트__ : funding-thumbnail, ranking
  - 펀딩리스트 불러오기 및 페이지네이션
  - 카테고리 리스트 불러오기
  - 랭킹 불러오기

### 유저페이지
[UserPage](/frontend/src/components/user/UserContent.vue)  
__포함 컴포넌트__ : funding-thumbnail
  - 내 계좌 내역 및 개설 기능 (개설시 개인키 1회 표시)
  - 참여 중인 기부/펀딩 리스트
  - 내가 개설한 기부/펀딩 리스트

### 펀딩 상세 페이지
[FundingPage](/frontend/src/components/funding/FundingContent.vue)  
__포함 컴포넌트__ : ranking, funding-addon
  - __funding-addon__ 컴포넌트를 통해 기부/펀딩 실행
  - 펀딩 현황 표시 - 시작일, 마감일, 모금액, 목표액, 개설자, 참여자

### 펀딩 개설 페이지
[FundingCreatePage](/frontend/src/components/funding/FundingCreate.vue)  
  - 펀딩 개설 기능
  - multipart, form-data 활용 이미지 파일 업로드
  - 입력 데이터 유효검사를 위한 Validation 기능

### 펀딩 승인 페이지
[FundingConfirmPage](/frontend/src/components/admin/FundingConfirm.vue)  
__포함 컴포넌트__ : funding-thumbnail
  - 관리자 계정 접근 여부 확인
  - 승인 대기중인 펀딩리스트 표시
  - 승인/거부 기능

---

<br/>

## 🧩Back End
Tool : __Spring Frame Work__

### API 명세서
[A506 특화프로젝트 명세서](https://docs.google.com/spreadsheets/d/1YNjDS0sABrG5Yy4ry1vV1dQMeDRhrKcBTGWiK5Ic1Hs/edit?usp=sharing)  

<br/>

### DB구조 (ERD)
![ERD](/uploads/78ec402853070bddc11dd3dfd422f10a/ERD.jpg)  

<br/>

### JPA
[Spring JPA docs](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)  
__JPA Repository, Query 통해 다양한 DB에 쉽게 적용 가능__
  - DB 테이블 구성을 JPA를 통해 구성  

<br/>

### JWT (JSON Web Token)
__Token을 활용하여 Session방식이 아닌 Token을 발급하여 로그인 이후 인증 매체로 활용__  

<br/>

### AWS S3
[AWS S3 Uploader](/backend/src/main/java/com/a506/mirinae/util/S3Uploader.java)  
__이미지 저장을 위해 AWS S3 서버를 대여__
__파일 업로드시 파일 변환 후 S3서버로 업로드, 파일명 DB 저장__  

<br/>

### Spring batch
__펀딩 마감시간 도래시 펀딩 종료를 진행하기 위한 Spring Batch Task__
[JobConfig](/backend/src/main/java/com/a506/mirinae/config/JobConfig.java)  
  - JOB(smartContractJob) > Step(smartContractStep) > Tasklet(FundingEndTasklet) 으로 구현
  - FundingEndTasklet은 RepeatStatus execute로 선언  

<br/>

### 구성
 - __controller__
   - Admin   : __관리자 권한 요청 처리__
     - 승인되지 않은 펀딩 리스트 제공
     - 펀딩 승인/거부
   - Funding : __펀딩 요청 처리__
     - 펀딩 리스트 제공
     - 펀딩 카테고리 리스트 제공
     - 펀딩 작성자 본인 확인
     - 펀딩 개설
     - 펀딩 참여
     - 펀딩 상세
     - 펀딩 취소
   - Ranking : __랭킹 요청 처리__
     - 펀딩 내 참여자 랭킹
     - 카테고리별 누적 개인 랭킹
   - Upload  : __이미지 업로드 요청 처리__
     - 펀딩 이미지 업로드
   - User    : __유저 요청 처리__
     - 소셜 로그인
     - 회원 정보 제공
     - 회원 정보 수정
     - 회원 탈퇴
     - 내가 참여한 펀딩 리스트
     - 내가 개설한 펀딩 리스트
     - 지갑 주소 저장  

<br/>

 - __domain__ ( Req,Res Bodies | Repositories by __JPA__)
   - category
   - donation
   - funding
   - user  

<br/>

 - __service__
   - Admin
     - 미승인 펀딩 리스트 조회 (getNotAcceptedFundingList)
     - 펀딩 승인/거부 (fundingStateChange)
   - ~~CustomUserDetail~~
   - Funding
     - 펀딩 리스트 조회 (getFundingList)
     - 펀딩 카테고리 리스트 조회 (getCategoryList)
     - 펀딩 작성자 본인 확인 (checkFundingOwner)
     - 펀딩 개설 (createFunding)
     - 펀딩 참여 (joinFunding)
     - 펀딩 상세 (detailFunding)
     - 펀딩 취소 (deleteFunding)
     - 펀딩 종료 (fundingEnd) - for Spring batch task (시간 만료 자동 실행)
   - Ranking
     - 펀딩 내 참여자 랭킹 (getFundingRanking)
     - 카테고리별 누적 개인 랭킹 (getCategoryRanking)
   - User
     - 소셜 로그인 (login) - return Token from __jwtTokenProvider__
     - 회원 정보 제공 (getUserInfo)
     - 회원 정보 수정 (updateUser)
     - 회원 탈퇴 (deleteUser)
     - 내가 참여한 펀딩 리스트 (getMyDonation)
     - 내가 개설한 펀딩 리스트 (getMyFunding)
     - 지갑 주소 저장 (saveWallet)  

<br/>

 - __util__
   - Deduplication - 내 기부 리스트 조회시 중복된 아이템 제거
   - EthereumUtil - web3j
   - FundingEndTasklet - for Spring batch task (시간 만료 자동 실행)
   - JwtAuthenticationFilter - JWT Token Filter
   - JwtTokenProvider - Token 생성, 조회 유효검사
   - S3Uploader - AWS S3 image Upload
   - ~~SmartContractScheduler~~  

<br/>

 - __config__
   - JobConfig - FundingEndTasklet을 활용한 batch 설정
   - SwaggerConfig - Swagger UI 사용을 위한 설정
   - WebMvcConfig
   - WebSecurityConfig - JWT Token Filter 외 다수 설정  

<br/>

## 🔗Block Chain
Tool : __Solidity__

__onlyOwner__ : Contract의 Owner만이 실행 가능

[FundingContract](/smart-contract/FundingContract.sol)  

### 구성
  1. 펀딩 열기 (openFunding) - onlyOwner
  2. 기부 하기 (donateFunding)
  3. 펀딩 종료 (closeFunding) - onlyOwner
  4. 펀딩 취소 (abortFunding) - onlyOwner

### 관리도구
  5. 펀딩ID로 조회시 기부 가능여부 반환 (checkFunding)
  6. 기부자가 펀딩ID로 조회시 기부한 금액 반환 (checkDonation)
  7. 이더 받기 (faucet) - __시연__을 위한 이더 무료나눔
  8. 이더 충전 (saveEth) - onlyOwner, Contract에 이더리움 충전
  9. 현재 기록된 펀딩번호 최대값 반환 (getMaxFundingId) - 추후 DB 복구에 필요한 값
  10. 이더 조회 (getValue) - Contract에 있는 이더리움 조회
  11. Owner체크 (onlyOwner) by modifier
