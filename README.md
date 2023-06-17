# chatbot-treats-webhook

` 🎁 Viber 메신저 플랫폼의 '대화형 봇' 을 통한 모바일 선물하기 서비스 🎁 `

* **프로젝트 명 : Viber 챗봇 기반 선물하기 서비스 구현**
* **프로젝트 기간 : 2023.06.08 ~ 2023.07.07**
* **Viber챗봇 링크**

> *설계 및 명세에 대한 내용은  `Wiki`를 참고해주세요.*
> *[Wiki 바로가기](https://github.com/sharetreats-team/chatbot-treats-webhook/wiki)*

<br>

## 개발환경

### 1) 주요 환경

- IntelliJ IDEA (Ultimate)
- Java 11
- Gradle 8.0
- Spring Boot 2.7.12

> *Java 11과 Spring Boot 2.7.12 버전을 선택한 이유* <br>
> *현재 기업 대부분 Java 8과 Java 11을 채택하여 사용하고 있는 경향이다. Java 8에서 라이브러리가 추가되었고, 성능이 개선된 G1 GC를 사용하는 Java 11을 선택하게 되었다.
Java 17도 현재 존재하지만, 현재 프로젝트를 진행하는 팀원들의 개발환경이 주로 Java 11, SpringBoot 2였다. 새로운 기능을 활용하지 않을 것으로 예상되는 상황에서 익숙한 개발 버전을 선택하는 것이 적절한 판단이라고 생각했다.
> Spring Boot는 3.x 버전 이상부터 Java 17 이상만 허용한다. 따라서 우리 팀은 2.7.x 버전을 선택하게 되었다.*

### 2) 세부 기술 스택

- (ORM) JPA
- Query DSL
- Spring Mail
- Viber bot

### 3) 데이터베이스

- (RDB) MySQL
- (In-memory) redis

### 4) 인프라

- AWS EC2 (HTTPS 네트워크 구축)
- AWS RDS (MySQL)

### 5) VCS(Version Controller System) & etc

- Git
- Notion, Slack

<br>

## 프로젝트 아키텍처

### CI/CD

<br>

## 커밋 메시지 규칙

### 1) 작성 규칙
* 작은 단위의 커밋과 구체적인 메시지 작성
* 이슈번호 [태그] 커밋 메시지
* `#1 [feat] 환영 메시지 전송 기능 구현`

### 2) 태그 설명
| 태그이름     | 설명                            |
|----------|---------------------------------|
| feat     | 새로운 기능 추가 및 코드 구현          |
| update   | 기존 코드에서 기능 추가 및 변경         |
| bug      | 버그 수정                          |
| docs     | 문서 수정                          |
| edit     | 오타 및 (기능에 영향 없는) 가벼운 수정   |
| refactor | 코드 리팩토링                       |

<br>

## 형상관리

### 1) 브랜치 전략

### 2) PR 및 코드리뷰 규칙

<br>

## Get started

<br>

---

## 팀 멤버

<br>

## 문의하기
- zlcls456@gmail.com
