# Service 패키지

- Data Access, Business logic 을 수행하는 클래스(인터페이스)를 관리합니다.
- Service 는 꼭 `인터페이스`와 `구현체 클래스`를 만들어주세요.
  - Webhook 에 인터페이스를 제공합니다.
  - ⭐️기능에 대한 설명은 `인터페이스`에 `JavaDoc(주석)을 통해` 구체적으로 문서화해주세요.
- `Transaction` 은 `Service Layer` 에서 관리합니다.