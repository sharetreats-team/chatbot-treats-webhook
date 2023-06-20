# chatbot-treats-webhook

` 🎁 Viber 메신저 플랫폼의 '대화형 봇' 을 통한 모바일 선물하기 서비스 🎁 `

* **프로젝트 명 : Viber 챗봇 기반 선물하기 서비스 구현**
* **프로젝트 기간 : 2023.06.08 ~ 2023.07.07**
* **Viber챗봇 링크**

> *설계 및 명세에 대한 내용은  `Wiki`를 참고해주세요.*
> *[Wiki 바로가기](https://github.com/sharetreats-team/chatbot-treats-webhook/wiki)*

<br>

## ✨  개발환경

### 1) 주요 환경

- IntelliJ IDEA (Ultimate)
- Java 11
- Gradle 8.0
- Spring Boot 2.7.12

### 2) 세부 기술 스택

- (ORM) JPA
- Query DSL
- Spring Mail
- Viber bot

### 3) 데이터베이스

- (RDB) MySQL
- (In-memory) Redis

### 4) 인프라

- AWS EC2 (HTTPS 네트워크 구축)
- AWS RDS (MySQL)

### 5) VCS(Version Controller System) & etc

- Git
- Notion, Slack

<br>

##  🅰 프로젝트 아키텍처

### 1) 시스템 아키텍처

![image (1)](https://github.com/sharetreats-team/chatbot-treats-webhook/assets/79829085/b48ed74c-c008-445d-9b62-f2a51c74c789)


### 2) CI/CD

<img alt="스크린샷 2023-06-19 오전 9 57 59" src="https://github.com/sharetreats-team/chatbot-treats-webhook/assets/79829085/fda52d09-ddb8-4007-9ce7-c211276528ae">

<br>

## 🆃 업무 진행 규칙
1️⃣ [Projects](https://github.com/orgs/sharetreats-team/projects/1) 페이지의 칸 반 보드에서 `Todo`를 확인합니다. <br>
2️⃣ 본인이 작업할 업무가 있으면 `In Progress`로 옮깁니다. (없을 경우 직접 새로 만들어주세요.) <br>
3️⃣ 'In Progress'로 옮긴 업무를 `Convert to issue`로 변경하고, 작업의 내용을 자세하게 작성합니다. <br>
4️⃣ 코드를 `fork`하고 `개인 repository` 에서 작업합니다. <br>
5️⃣ 작업을 완료하면 `original repository`의 `test` 브랜치로 PR을 보냅니다. <br>
6️⃣ 코드리뷰를 진행하고, 모든 승인을 받으면 `Merge` 합니다.
7️⃣ Merge가 성공하면, Issue는 자동으로 종료합니다. 또한 `In Progress`에 있던 작업 티켓도 `Done`으로 자동으로 이동합니다.


## 🅲 커밋 메시지 규칙

### 1) 작성 규칙
* 작은 단위로 커밋해주세요.
* 어떻게 보다는 __무엇과 왜를 구체적으로__ 설명해주세요.
  * __타이틀은 목적__ 이 드러나게 작성해주세요. 
  * __본문은 진행 이유__ 가 드러나게 작성해주세요.
* 작성 형식
  * 태그: 커밋 메시지 (이슈번호)
  * ex) `feat: 환영 메시지 전송 기능 구현 (#1)`

### 2) 태그 설명
| 태그이름     | 설명                         |
|----------|----------------------------|
| feat     | 새로운 기능 추가 및 코드 구현          |
| update   | 이미 구현된 코드에서 기능 추가 및 변경     |
| bug      | 버그 수정                      |
| docs     | 문서 작성 및 수정                 |
| refactor | 코드 리팩토링 (성공적으로 배포한 코드를 변경) |

<br>

## ᛘ 형상관리

### 1) 브랜치 전략
> github-flow 전략 사용
> 1) 기능 테스트를 위해서는 배포가 되어야 함 -> 배포 빈도가 잦을 것으로 예상
> 3) 따라서 브랜치를 복잡하게 나누지 말고, 작업에 맞게 하위 브랜치만 뽑는 전략
> 4) 또는 fork 하여 개인 repo 에서 작업을 진행하고, PR을 보낸다.
> 5) 추가로 git-flow 전략의 방식을 융합하여 main 브랜치에 merge 하기 전에 테스트 진행

| 브랜치명         | 설명                                          |
|--------------|---------------------------------------------|
| main         | 서비스 운영이 가능한 수준의 최종 브랜치  |
| test       | 배포 테스트를 위한 브랜치 , 최종 브랜치 이전에 오류 해결|



### 2) PR 및 코드리뷰 규칙

- `매일 17시` 진행
- 본인이 작업한 코드를 발표
- 발표 후, PR 페이지에서 코드리뷰 진행
- `merge`를 위해서 팀원 전원(4인)에게 `approve`를 받아야합니다.
- PR 본문은 자세하게 작성
  - `This closes #이슈번호` 를 꼭 작성합니다. 


### 3) 배포 작업 규칙

- `매일 17시` 이후 배포를 진행
- 문제가 없는 작업(브랜치)을 `main 브랜치`로 `merge` 하여 자동 배포
- 테스트를 진행하고 개선사항 및 다음 작업 점검


<br>

## ⏯ Get started

> 아래 방법을 통해 `챗봇 선물하기 서비스`를 경험할 수 있습니다.

<br>

---

## 👨‍💻 팀 멤버

| [<img src="https://avatars.githubusercontent.com/u/61264510?v=4" width="130px;" alt=""/>](https://github.com/Damm06) | [<img src="https://avatars.githubusercontent.com/u/79829085?v=4" width="130px;" alt=""/>](https://github.com/Si-Hyeak-KANG) | [<img src="https://avatars.githubusercontent.com/u/104256143?v=4" width="130px">](https://github.com/Daeell) | [<img src="https://avatars.githubusercontent.com/u/82653075?v=4" width="130px" >](https://github.com/tlqkrus012345) |
|:--------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------:|
|                                         [🌟 채화담](https://github.com/Damm06)                                          |                                           [강시혁](https://github.com/Si-Hyeak-KANG)                                           |                                       [김다엘](https://github.com/Daeell)                                       |                                       [이규호](https://github.com/tlqkrus012345)                                       |

<br>

## 📧 문의하기

- zlcls456@gmail.com
