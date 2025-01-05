# 프로젝트 학습 목표

1. HTTP Method 7 things 학습
2. Request Body, Query Params, Path Params 학습
3. Test Code BDD(behavior Driven Development)형식으로 작성
4. 정적 펙토리 메서드 (Static Factory Method)
5. 일급 컬렉션 (First Class Collection) 학습
6. One method, one responsibility
7. Law of Demeter and Tell, Don't Ask
8. setter 를 지양
9. Mapper 사용
10. 빌터 패턴 학습

---

# I Learned

# application.yml 파일 설정

### datasource 설정
- "useSSL = false": DB 연결 시 SSL(Secure Sockets Layer)보안 연결을 사용하지 않도록 설정. SSL 경고를 피할 수 있다.
개발환경에서는 false설정이 일반적, 운영환경에서 true와 함께 SSL 인증서 설정을 사용하는것을 권장
- "allowPublicKeyRetrieval=true":  서버의 PK를 직접 요청할 수 있도록 허용

# Dto
- API마다 Dto를 만들어 주는것이 유지보수 측면에서 좋다. 때문에 API마다 Dto를 만들어서 클래스에 Builder를 붙여 쓰는것을 권장.

# Controller 레이어

- Spring MVC 에서 URL 경로의 일부를 변수로 바인딩하기 위해 사용하는 어노테이션 {} 부분을 바인딩

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