## 프로젝트명 :

# 목차

1. [프로젝트 소개](#프로젝트-소개)
2. [프로젝트 명세](#프로젝트-명세)
   - [배포 환경](#배포-환경)
   - [개발 환경](#개발-환경)
   - [Design Resources](#Design-Resources)
   - [핵심 라이브러리](#핵심-라이브러리)
3. [Git convention](#Git-convention)

# 프로젝트 소개

# 프로젝트 명세

### 팀원 역할

김호석(팀장) -  
노진혁 -  
신한규 -  
이예은 -  
이희정 -

### 배포 환경

URL :  
배포 여부 : X  
접속 가능 :  
HTTPS 적용 :

### 개발 환경

Front-end  
Framework :  
지원 환경 :  
담당자 :

Back-end  
Framework :  
Database :  
담당자 :

Design  
Framework :  
Design Tool 사용 : X  
담당자 :

Blockchain  
Framework :  
Design Tool 사용 : X  
담당자 :

### Design Resources

외부 템플릿 또는 에셋

자체 제작 산출물 (필요시 이미지 또는 설명 첨부)

### 핵심 라이브러리

# Git convention

### Commit

```
[#Jira 이슈번호] type: 행위 요약

body
```

```
[#S05P12A607-141] feat: 회원정보 등록 api 구현

행위에 대한 자세한 설명 (What & Why)
```

- **type 종류**

| type     | 설명                                 |
| -------- | ------------------------------------ |
| feat     | 새로운 기능에 대한 커밋              |
| fix      | 버그 수정에 대한 커밋                |
| build    | 빌드 관련 파일 수정에 대한 커밋      |
| chore    | 그 외 자잘한 수정에 대한 커밋        |
| ci       | CI 관련 설정 수정에 대한 커밋        |
| docs     | 문서 수정에 대한 커밋                |
| style    | 코드 스타일 혹은 포맷 등에 관한 커밋 |
| refactor | 코드 리팩토링에 대한 커밋            |
| test     | 테스트 코드 수정에 대한 커밋         |
| design   | CSS 등 UI 수정에 대한 커밋           |
| comment  | 주석 추가 및 수정에 대한 커밋        |

### Branch

master

|

develop

|

feature (각 기능 단위)

- develop은 develop-be, develop-fe로 나뉨

  - back-end 관련 작업과 front-end 관련 작업을 각각 처리

- feature의 기능 단위는 Jira Sub-Task 기준

  - 필요에 따라 통합해서 사용해도 괜찮음

- 예시
  - feature/fe-**signup**
  - feature/be-**signup**

<br>
