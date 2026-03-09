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
- [ ] 미션 5 : QueryDSL 동적 검색

## API 명세

### Restaurant API

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/v1/restaurants` | 맛집 목록 조회 |
| GET | `/api/v1/restaurants/{id}` | 맛집 단건 조회 |
| POST | `/api/v1/restaurants` | 맛집 등록 |
| PUT | `/api/v1/restaurants/{id}` | 맛집 수정 |
| DELETE | `/api/v1/restaurants/{id}` | 맛집 삭제 |

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

## 실행 방법

```bash
./gradlew bootRun
```

H2 콘솔: `http://localhost:8080/h2-console`
