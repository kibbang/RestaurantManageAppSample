# Restaurant Manage

Spring Boot 기반 맛집 관리 RESTful API 토이 프로젝트

## 프로젝트 목적

Spring, JPA, Spring Data JPA, QueryDSL 실습을 위한 프로젝트입니다.

## 기술 스택

| 구분 | 기술 |
|------|------|
| Framework | Spring Boot 4.0.3 |
| Language | Java 21 |
| ORM | JPA / Hibernate, Spring Data JPA |
| Query | QueryDSL |
| Database | H2 (In-Memory) |
| Build | Gradle 9.3 |
| Library | Lombok, Bean Validation |

## 엔티티 설계

```
Restaurant (맛집)
├── Menu (메뉴) - ManyToOne
└── Visit (방문기록) - ManyToOne
```

### Restaurant (맛집)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | PK |
| name | String | 가게명 |
| address | String | 주소 |
| foodCategory | Enum | 한식/중식/일식/양식/기타 |
| description | String | 설명 |
| createdAt | LocalDateTime | 등록일 |
| updatedAt | LocalDateTime | 수정일 |

### Menu (메뉴)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | PK |
| name | String | 메뉴명 |
| price | Integer | 가격 |
| description | String | 설명 |
| isPopular | Boolean | 인기메뉴 여부 |
| restaurant | Restaurant | FK (ManyToOne) |

### Visit (방문기록)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | PK |
| visitDate | LocalDate | 방문일 |
| rating | Integer | 평점 (1~5) |
| review | String | 한줄평 |
| createdAt | LocalDateTime | 등록일 |
| restaurant | Restaurant | FK (ManyToOne) |

## 주요 연습 포인트

- **JPA** : 엔티티 설계, 연관관계 매핑 (1:N), 영속성 컨텍스트
- **Spring Data JPA** : Repository 인터페이스, 쿼리 메서드
- **QueryDSL** : 카테고리별 필터링, 평점 평균 계산, 동적 검색 조건
- **REST API** : CRUD + 검색 API 설계

## 미션 진행 현황

- [x] 미션 1 : Restaurant 도메인 생성
- [x] 미션 2 : Restaurant CRUD API
- [x] 미션 3 : Menu 도메인 및 API
- [x] 미션 4 : Visit 도메인 및 API
- [x] 미션 5 : QueryDSL 동적 검색 및 상세 조회

## API 명세

### Restaurant API

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/v1/restaurants` | 맛집 목록 조회 |
| GET | `/api/v1/restaurants/{id}` | 맛집 단건 조회 |
| POST | `/api/v1/restaurants` | 맛집 등록 |
| PUT | `/api/v1/restaurants/{id}` | 맛집 수정 |
| DELETE | `/api/v1/restaurants/{id}` | 맛집 삭제 |
| GET | `/api/v1/restaurants/search` | 맛집 동적 검색 (이름, 카테고리, 주소, 최소평점) |
| GET | `/api/v1/restaurants/{id}/detail` | 맛집 상세 조회 (메뉴, 방문기록 포함) |

### Menu API

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/v1/restaurants/{restaurantId}/menus` | 맛집 메뉴 목록 조회 |
| GET | `/api/v1/menus/{id}` | 메뉴 단건 조회 |
| POST | `/api/v1/restaurants/{restaurantId}/menus` | 메뉴 등록 |
| PUT | `/api/v1/menus/{id}` | 메뉴 수정 |
| DELETE | `/api/v1/menus/{id}` | 메뉴 삭제 |

### Visit API

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/v1/restaurants/{restaurantId}/visits` | 맛집 방문기록 목록 조회 |
| GET | `/api/v1/visits/{id}` | 방문기록 단건 조회 |
| POST | `/api/v1/restaurants/{restaurantId}/visits` | 방문기록 등록 |
| PUT | `/api/v1/visits/{id}` | 방문기록 수정 |
| DELETE | `/api/v1/visits/{id}` | 방문기록 삭제 |

### 검색 파라미터

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| name | String | 맛집 이름 (부분 일치) |
| foodCategory | Enum | 음식 카테고리 (KOREAN, CHINESE, JAPANESE, WESTERN, ETC) |
| address | String | 주소 (부분 일치) |
| minRate | Double | 최소 평균 평점 |
| page | int | 페이지 번호 (0부터) |
| size | int | 페이지 크기 |

## 프로젝트 구조

```
src/main/java/hello/restaurantmanage/
├── common/                    # 공통 클래스
│   ├── ApiResponse.java       # API 응답 래퍼
│   └── BaseTimeEntity.java    # 생성/수정일 자동 관리
├── controller/api/            # REST 컨트롤러
│   ├── RestaurantApiController.java
│   ├── MenuApiController.java
│   └── VisitApiController.java
├── domain/                    # JPA 엔티티
│   ├── Restaurant.java
│   ├── Menu.java
│   └── Visit.java
├── dto/
│   ├── request/               # 요청 DTO
│   ├── response/              # 응답 DTO
│   └── search/                # 검색 조건 DTO
├── enums/
│   └── FoodCategory.java
├── repository/                # Spring Data JPA + QueryDSL
│   ├── RestaurantRepository.java
│   ├── RestaurantCustomRepository.java
│   ├── RestaurantCustomRepositoryImpl.java
│   ├── MenuRepository.java
│   └── VisitRepository.java
└── service/                   # 비즈니스 로직
    ├── RestaurantService.java
    ├── RestaurantDetailService.java
    ├── MenuService.java
    └── VisitService.java
```

## 실행 방법

```bash
./gradlew bootRun
```

H2 콘솔: `http://localhost:8080/h2-console`
