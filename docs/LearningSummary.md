# I Learned

# Restful 하게 만들자

1. 자원(엔티티 테이블 등)의 접근을 URI로 표현
   - 사용 예시) /user, /user/{id}
2. HTTP Method로 자원에 대한 행위를 표현
   - 사용 예시) 
     - GET: 데이터 조회 -> 서버의 상태를 변경하지 않는다
     - POST: 데이터 생성/처리 -> 서버의 상태를 변경시킴
     - PUT: 리소스 전체 수정/생성
       -> 리소스 전체를 수정하거나 새로 생성할 때 사용.
       -> 데이터가 기존에 있었으면 수정 없으면 생성
     - PATCH: 리소스 일부 수정
       -> PUT과 비슷하지만, 리소스의 일부만 수정한다. 요청 본문에 수정할 데이터만 포함
     - DELETE: 서버 리소스 삭제
     - HEAD: 메타데이터 조회
     - OPTIONS: 서버가  지원하는 HTTP 메서드 확인용도
     - TRACE: 요청 경로 추적
     - CONNECT: 터널링
3. 정의되어 있는 HTTP 상태코드를 잘 사용하자

# application.yml 파일 설정

### datasource 설정
- "useSSL = false": DB 연결 시 SSL(Secure Sockets Layer)보안 연결을 사용하지 않도록 설정. SSL 경고를 피할 수 있다.
개발환경에서는 false설정이 일반적, 운영환경에서 true와 함께 SSL 인증서 설정을 사용하는것을 권장
- "allowPublicKeyRetrieval=true":  서버의 PK를 직접 요청할 수 있도록 허용

# Dto
- API마다 Dto를 만들어 주는것이 유지보수 측면에서 좋다. 때문에 API마다 Dto를 만들어서 클래스에 Builder를 붙여 쓰는것을 권장.

# Controller 레이어

- Spring MVC 에서 URL 경로의 일부를 변수로 바인딩하기 위해 사용하는 어노테이션 {} 부분을 바인딩
- ResponseEntity란? HTTP 응답 상태 코드, 응답 본문, 응답 헤더를 직접 설정할 수 있는 클래스로 응답 데이터가 JSON형태로 자동 직렬화 되어 클라이언트에 반환.
Spring Boot에서 제공하는 클래스.

# Service 레이어

### @Transactional

- 읽기 전용 메서드(get요청 등)에는 보통 DB변경이 없으므로 readOnly=true로 최적화 하는것이 좋다.
- 메서드 마다 propagation(전파 옵션)이 다를 수 있으므로 클래스 레벨에서 @Transactional 어노테이션을 붙이는건 좋지 않다.

# test 코드 작성
- jsonpath: JSON응답에서 특정 값을 검증학 위한 메서드로 MockMvc 테스트에서 JSON응답 본문을 경로를 통해 특정 필드의 값을 추출하고 검증할 수 있게 해준다.

---

# Trouble shooting

1. 문제 상황 
: 컨트롤러에서 @GetMapping을 사용해 id값을 요청하려 했지만, 클라이언트 요청에서 전달된 파라미터를 제대로 받아오지 못하는 문제가 발생

- 원인 분석: 오류 메시지에 'Required type: String, Provided: Long'라는 내용이 표시 , 이를 보고 Gradle 의존성 설정에 문제가 있는 것으로 판단
- 문제 해결: 인텔리제이 설정에서 빌드 도구를 IntelliJ가 아닌 Gradle로 변경하였더니 문제가 해결. IntelliJ로 빌드하면 IDE가 자체적으로 build.gradle 
대신 프로젝트 설정을 기반으로 classPath를 구성하게 되어 나타나는 문제 였다.