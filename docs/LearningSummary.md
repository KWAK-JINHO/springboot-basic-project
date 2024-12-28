>프로젝트에 적용해보고 싶은 학습목표

# HTTP Method 7 things

- GET: 데이터 조회 -> 서버의 상태를 변경하지 않는다

- POST: 데이터 생성/처리 -> 서버의 상태를 변경시킴

- PUT: 리소스 전체 수정/생성
  - 리소스 전체를 수정하거나 새로 생성할 때 사용.
  - 데이터가 기존에 있었으면 수정 없으면 생성

- PATCH: 리소스 일부 수정
  - PUT과 비슷하지만, 리소스의 일부만 수정한다. 요청 본문에 수정할 데이터만 포함

- DELETE: 서버 리소스 삭제

- HEAD: 메타데이터 조회
  - GET 요청과 동일하지만, 응답 본문을 제외하고 헤더만 반환. 리소스가 존재하는지 확인하거나 메타데이터를 얻을 때 사용.

- OPTIONS: 서버가  지원하는 HTTP 메서드 확인용도
  - 특적 URL 또는 전체 서버에 대한 메서드 지원정보를 반환.
  - 사용예시: OPTIONS /users

- TRACE: 요청 경로 추적
  - 요청이 서버까지 가능 경로를 추적할 때 사용, 디버깅 목적이나 실무에서 사용거의 x

- CONNECT: 터널링
  - 터널링을 시작할 때 사용 (예: HTTPS 에서 SSL/TLS 연결)
  - 주로 프록시 서버와 함께 사용.

# What is Routing?

- 클라이언트가 보낸 요청(URL)을 적절한 Handler 또는 Controller로 연결해주는 과정

# Request Body, Query Params, Path Params

## Request Body

- 요청의 **본문**에 데이터를 담아 서버로 전달(상세 내용)
- POST, **PUT**,PATCH 요청에서 사용
- JSON, XML, form-data등

- 사용 예시
```
{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "123456"
}
```
	
## Query Params

- URL의 쿼리 문자열로 포함되는 매개변수
- GET요청 사용
- 필터링, 정렬, 검색 등 요청에 필요한 추가 데이터 전달

- 사용 예시
`?key1=value1&key2=value2` -> /api/products?category=books&sort=price
	
## Path Params

- URL 경로의 일부로 포함되어 요청의 리소스 식별
- 특정리소스 접근

- 사용예시)
`/resource/{id}`  ->  /api/users/123

# Test Code

## BDD (behavior Driven Development)

- 애플리케이션의 기능이 비즈니스 요구사항에 부합하는지 확인하는데 중점을 두는 방식

### @Nested

- 계층 구조 테스트 코드를 작성할 수 있게 해준다.
  - 관련있는 테스트끼리 묶어서 @nested로 표시. -> 가독성 향상 및 테스트 편의성 향상

## 단위 테스트 VS 통합 테스트

### 단위테스트

- 하나의 모듈을 기준으로 독립적으로 진행되는 테스트. 하나의 모듈은 하나의 기능 또는 메소드로 이해하면 된다.

- 장점
 - 해당 부분만 테스트하기 때문에 빠른 문제 여부 판단 가능

### 통합 테스트

- 모듈을 통합하는 과정에서 모듈간의 호환성을 확인하기 위한 테스트

- 장점
 - 가짜 객체(Mock Object)를 주입하여 정해진 답변을 테스트

## 작성 준비

- assertEquals()와 같은 메소드는 AssertJ가 가독성이 좋아 둘을 조합해 많이 사용

## 작성시 주의사항

- 테스트 설명을 작성할 때 비문이 아닌 완전한 문장으로 작성.
- 빠른 테스트 가능
- 각각의 테스트는 독립적이여야 한다.
- 어느 환경에서도 반복 가능해야한다.
- 성공 또는 실패로 결과를 내어야 한다.
- 실제 코드를 구현하기 직전에 구현해야 한다.

# Static Factory Method

- 객체 생성의 역할을 하는 클래스 메서드 -> 외부에서 원하는 객체를 반환.

## 생성자와 정적 펙토리 메서드

- 정적 펙토리 메서드가 생성자보다 우위를 차지하고 있는 점
  - 메서드 이름에 객체의 생성 목적을 담아낼 수 있다.
  - 자주 사용되는 요소의 개수가 정해져있다면 해당 개수만큼 미리 생성해놓고(캐싱) 조회하는 구조로 만들 수 있다.
  
- 사용예시)
```java
public class LottoNumber {
  private static final int MIN_LOTTO_NUMBER = 1;
  private static final int MAX_LOTTO_NUMBER = 45;

  private static Map<Integer, LottoNumber> lottoNumberCache = new HashMap<>();

  static {
    IntStream.range(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER)
                .forEach(i -> lottoNumberCache.put(i, new LottoNumber(i)));
  }

  private int number;

  private LottoNumber(int number) {
    this.number = number;
  }

  public LottoNumber of(int number) {  // LottoNumber를 반환하는 정적 팩토리 메서드
    return lottoNumberCache.get(number);
  }

  ...
}
```

- 하위 자료형 객체를 반환 가능하다.
- 객체 생성을 캡슐화할 수 있다.
  -> 정적 펙토리 메서드는 DTO와 Entity간의 자유로운 형변환이 가능하게 해준다.
  -> 네이밍 컨벤션
    - `from`: 하나의 매개 변수를 받아서 객체를 생성
    - `of`: 여러개의 매개 변수를 받아서 객체를 생성
    - `getInstance``instance`: 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
    - `newInstance``create`: 새로운 인스턴스를 생성
    - `get[OtherType]`: 다른 타입의 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
    - `new[OtherType]`: 다른 타입의 새로운 인스턴스를 생성.

# First Class Collection

- 사용조건
  - 콜렉션을 포함한 클래스는 반드시 다른 멤버 변수가 없어야 한다.

- 사용 예시
```java
Map<String, String> map = new HashMap<>();
map.put("1", "A");
map.put("2", "B");
map.put("3", "C");
```
이 코드를 아래와 같이 Wrapping
```java
public class GameRanking {

    private Map<String, String> ranks;

    public GameRanking(Map<String, String> ranks) {
        this.ranks = ranks;
    }
}
```
## 일급 컬렉션의 사용이유

### 1. 비지니스에 종속적인 자료구조

- 사용 예시

- 로또 복권 게임
```
public void createLottoNumber() {
	List<Long> lottoNumbers = createNumDuplicateNumbers();
	validateSize(lottoNumbers);
	validateDuplicate(lottoNumbers);
	
	...
	
}
```

에서 List< Long >은 요구사항에 따라 "6개의 숫자로만 이루어져야 한다.", "6개의 숫자는 서로 중복되지 않아야만 한다." 라는 검증 로직을 필요로 할 수 있다.
이 때 직접 해당 조건으로만 생성할 수 있는 자료구조 즉, 아래와 같이 '일급 컬렉션' 을 만든다.

```
public LottoTicket(List<Long> lottoNubers) {
	validateSize(lottoNumbers);
	validateDuplicate(lottoNumbers);
	this.lottoNumbers = lottoNumbers;
}
```

### 2. Collection의 불변성을 보장

기본형은 변경 불가하지만 참조형은 다른 객체를 참조하는것만 막고 객체의 내부 상태(필드)는 변경이 가능하다. 따라서 참조형인 collection은 내부에 add, remove 등 가능
때문에 Java에서는 일급 컬렉션과 래퍼 클래스등 으로 불변을 만들어 주어야 한다.

``` 불변을 보장
public class Orders {
	private final List<Order> orders;

	public Orders(List<Order> order) {
		this.orders = orders;
	}

	public long getAmountSum() {
		return orders.stream()
				.mapToLong(Order::getAmount)
				.sum();
	}
}
```
- Orders 클래스는 클래스를 새로 만들거나 값을 가져오는것 밖에 안되는 클래스이다. -> 값의 변경/추가가 안됨

### 3. 상태와 행위를 한 곳에서 관리

```aiignore
@Test
public void 로직과_값이_따로() {
    // given ( 값은 여기)
    List<Pay> pays = Arrays.asList(
        new Pay(NAVER_PAY, 1000),
        new Pay(KAKAO_PAY, 2000),
        new Pay(PAYCO, 1500),
        new Pay(TOSS, 1000);
    )
    // when (계산 로직은 여기)
    Long naverPaySum = pays.stream()
            .filter(pay -> pay.getPayType().equals(NAVER_PAY))
            .mapToLong(Pay::getAmount)
            .sum();
    // then
    assertThat(naverPaySum).isEqualsTo(2500);
}
```
이 또한 일급 컬렉션으로 해결
```
public class PayGroups {
    private List<Pay> pays;

    public PayGroups(List<Pay> pays) {
        this.pays = pays;
    }

    public Long getNaverPaySum() {
        return pays.stream()
                .filter(pay -> PayType.isNaverPay(pay.getPayType()))
                .mapToLong(Pay::getAmount)
                .sum();
    }
}
```
다른 결제 수단이 필요하다면 메서드를 쉽게 추가

```aiignore
@Test
public void 값과_로직이_함께() {
    // given ( 값은 여기)
    List<Pay> pays = Arrays.asList(
        new Pay(NAVER_PAY, 1000),
        new Pay(KAKAO_PAY, 2000),
        new Pay(PAYCO, 1500),
        new Pay(TOSS, 1000);
    )
    PayGroups payGroups = new PayGroups(pays);
    
    // when (계산 로직은 여기)
    Long naverPaySum = payGroups.getNaverPaySum();

    // then
    assertThat(naverPaySum).isEqualsTo(2500);
}
```

### 4. 이름을 다르게 지어줄 수 있다.

# One method, one responsibility

# Law of Demeter and Tell, Don't Ask

- 메시지를 보내는 객체는 받는 객체가 누군지 모르지만 믿고 보낸다. -> 객체를 자율적으로 만들며 캡슐화를 보장, 결합도를 낮춘다.

## Law of Demeter

- 객체는 내부구조를 공개하면 안되며, 대신 함수를 공개한다.
- 클라이언트는 서버에 의해 헨더링 되는 서비스가 필요하면 내부에 접근대신, 인터페이를 제공받아 사용한다.

# setter 를 지양해라

- setter의 무분별한 사용은 객체지향의 핵심인 캡슐화와 책임 분리를 약화시킨다.

1) 필드 값을 외부에서 직접 변경하게 하지말고, (의미를 드러내는)메서드를 제공하여 상태를 제어
2) DTO로 단순 데이터 전달
3) Immutable 객체로 설계(setter의 제거)

# 엔티티와 DTO

## DTO

- DTO는 계층간의 데이터 전송을 위한 객체로 프레젠테이션 레이어와 서비스 레이어 사이에서 사용되며 Repository에는 전달하지 않는 것이 좋다.
그 이유는 Repository는 DB와 엔티티간의 매핑만을 책임져야 하기 때문에 Repo의 책임이 늘어나며, Repository는 엔티티를 매개변수로 받아 DB와 엔티티간의 매핑만
처리하는 것이 바람직하다.

## Mapper

- Mapper를 사용하면 변환로직을 엔티티와 DTO에 포함시키지 않을 수 있다.
- Mapper는 독립된 클래스로 단위 테스트를 별도로 작성할 수 있다.
- Mastruct등 라이브러리를 통한 자동화가 가능

# 빌터 패턴

## 빌더 패턴이란?

- 필수 매개변수(private final)로 객체를 생성하고, 선택 매개변수를 초기화 한 뒤 build() 메서드를 호출해 불변 객체를 얻는다.
- 클라이언트는 필수 매개변수 만으로 생성자를 호출해 빌더 객체를 얻을 수 있다.
- 생성할 클래스 안에 정적 멤버 클래스로 만들어두는 게 보통이다. (lombok 사용 가능)
- 1) 생성자 매개변수가 적거나 2) 선택적 매개변수가 거의 없는 경우에는 builder 패턴은 성능에 민감한 상황에서 문제가 될 수 있다.(생성비용 적음)

## builder 패턴을 사용하지 않고 간단히 처리하는 방법

- 오버로딩된 생성자 -> 생성자를 여러개 정의하여 간단한 매개변수 조합 처리
- Static Factory Method -> 객체 생성 로직을 간결하고 직관적으로 만들 수 있다.