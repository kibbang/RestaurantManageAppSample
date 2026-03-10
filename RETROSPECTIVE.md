# 프로젝트 회고 - Restaurant Manage

## 프로젝트 개요

Spring Boot + JPA + QueryDSL을 활용한 맛집 관리 REST API 토이 프로젝트를 5개 미션으로 나누어 진행했습니다.

## 미션별 회고

### 미션 1: Restaurant 도메인 생성
- JPA 엔티티 설계의 기본기를 다졌습니다.
- `BaseTimeEntity`를 통해 생성일/수정일 자동 관리 패턴을 익혔습니다.
- `@NoArgsConstructor(access = PROTECTED)` 같은 JPA 엔티티 설계 관례를 학습했습니다.

### 미션 2: Restaurant CRUD API
- Spring MVC의 `@RestController` 기반 REST API 설계를 실습했습니다.
- `ApiResponse<T>` 공통 응답 래퍼를 만들어 일관된 API 응답 구조를 구현했습니다.
- Entity와 DTO를 분리하고 팩토리 메서드(`from()`) 패턴을 적용했습니다.

### 미션 3: Menu 도메인 및 API
- `@ManyToOne` 연관관계 매핑과 `LAZY` 로딩 전략을 이해했습니다.
- 코드 리뷰를 통해 Bean Validation 어노테이션(`@NotBlank`, `@Min` 등) 활용법을 개선했습니다.
- 하위 리소스의 URL 설계(`/restaurants/{id}/menus`) 패턴을 학습했습니다.

### 미션 4: Visit 도메인 및 API
- 도메인 로직에서의 유효성 검증(`validRating`) 구현을 실습했습니다.
- 코드 리뷰를 통해 응답 타입의 정확성과 팩토리 메서드 설계를 개선했습니다.
- 각 미션마다 코드 리뷰 -> 수정 사이클을 통해 코드 품질이 점진적으로 향상되었습니다.

### 미션 5: QueryDSL 동적 검색 및 상세 조회
- QueryDSL의 `JPAQueryFactory`를 활용한 동적 쿼리 작성법을 학습했습니다.
- `BooleanExpression`을 반환하는 조건 메서드 패턴으로 깔끔한 동적 검색을 구현했습니다.
- `Projections.constructor()`를 활용한 DTO 직접 조회를 실습했습니다.
- `leftJoin` + `groupBy` + `having`을 활용한 집계 쿼리를 작성했습니다.
- Custom Repository 패턴(`RestaurantCustomRepository` + `Impl`)을 학습했습니다.
- 상세 조회 시 N+1 문제를 `@OneToMany` 양방향 매핑 대신 `findByRestaurantIdIn()` IN절 쿼리로 해결하여, 단방향 매핑을 유지하면서도 쿼리 최적화를 달성했습니다.

## 코드 리뷰를 통해 배운 점

### GET 요청에서의 파라미터 바인딩
- GET 요청에 `@RequestBody`를 사용하면 HTTP 스펙에 위배됩니다.
- 검색 조건은 `@ModelAttribute`를 사용해 쿼리 파라미터로 받아야 합니다.
- `@ModelAttribute` 바인딩을 위해서는 `@NoArgsConstructor`가 필요합니다.

### 페이지네이션
- QueryDSL에서 `offset()`과 `limit()`을 반드시 적용해야 실제 페이지네이션이 동작합니다.
- Count 쿼리는 메인 쿼리와 분리하되, `fetch().size()` 대신 효율적인 방법을 사용해야 합니다.

### DTO 불변성
- Response DTO에 `@Setter`를 사용하면 불변성이 깨져 데이터 변조 가능성이 생깁니다.
- `final` 필드와 private 생성자 + 팩토리 메서드 조합으로 불변 DTO를 만들어야 합니다.

### 팩토리 메서드 설계
- 파라미터가 많은 `from()` 메서드는 Entity를 직접 받도록 리팩토링하면 가독성이 크게 개선됩니다.
- `from(Long, String, String, ...)` 보다 `from(Restaurant, List<MenuResponse>, ...)` 형태가 의도가 명확합니다.

### 예외 처리
- `orElseThrow()`에 메시지를 포함해야 디버깅이 수월합니다.
- Service 클래스에 `@Transactional(readOnly = true)` 기본 설정을 적용하는 것이 좋습니다.

### N+1 쿼리 해결 - IN절 vs 양방향 매핑
- `@OneToMany` 양방향 매핑 + `@EntityGraph`로 한번에 조회하려 했으나, `MultipleBagFetchException`(두 개의 List를 동시 Fetch Join 불가) 문제 발생
- 대안으로 `findByRestaurantIdIn(List<Long>)` IN절 쿼리를 적용하여, 단방향 매핑을 유지하면서도 다건 조회 시 N+1을 방지
- 양방향 매핑은 순환 참조, 영속성 컨텍스트 관리 복잡성 등의 단점이 있으므로 IN절 방식이 더 실용적

## 아쉬운 점 / 개선하고 싶은 점

1. **커스텀 예외 클래스 미적용**: `IllegalArgumentException` 대신 `RestaurantNotFoundException` 같은 도메인 예외를 만들면 예외 처리가 더 체계적일 것입니다.
2. **GlobalExceptionHandler 미구현**: `@ControllerAdvice`를 활용한 전역 예외 처리가 없어 API 에러 응답이 일관되지 않습니다.
3. **테스트 코드 개선**: 테스트 데이터 셋업 코드가 반복되고, 페이지네이션/정렬 검증이 부족합니다.

## 전체 소감

JPA와 Spring Data JPA의 기본기부터 QueryDSL의 동적 쿼리까지 단계별로 학습할 수 있었습니다. 특히 매 미션마다 코드 리뷰를 거치면서 단순한 기능 구현을 넘어 API 설계 원칙, DTO 불변성, 예외 처리 등 실무에서 중요한 관점들을 체득할 수 있었습니다.
