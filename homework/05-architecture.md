## 핵사고날 아키텍처 vs 클린 아키텍처

예시 API
```http
GET    /users/1
POST   /users
PATCH  /users/1/password
DELETE /users/1
```

User Aggregate
```
- User
- UserId
- Email
- Password
- UserStatus
```

> 아래 구조는 DDD를 적용합니다.

### 1. Hexagonal + DDD 구조

핵심은 Domain 중심 + Port/Adapter 분리입니다.

```text
user
├── domain # 순수 도메인 계층
│   ├── model
│   │   ├── User.kt
│   │   ├── UserId.kt
│   │   ├── Email.kt
│   │   ├── Password.kt
│   │   └── UserStatus.kt
│   ├── repository
│   │   └── UserRepository.kt
│   └── service
│       └── UserDomainService.kt  # 도메인 비즈니스 서비스
│
├── application
│   ├── port
│   │   └── `in`
│   │       ├── GetUserUseCase.kt
│   │       ├── RegisterUserUseCase.kt
│   │       ├── ChangeUserPasswordUseCase.kt
│   │       └── DeleteUserUseCase.kt
│   └── service
│       ├── GetUserService.kt
│       ├── RegisterUserService.kt
│       ├── ChangeUserPasswordService.kt
│       └── DeleteUserService.kt
│
├── adapter
│   ├── `in`
│   │   └── web
│   │       ├── UserController.kt
│   │       └── dto
│   │           ├── UserResponse.kt
│   │           ├── RegisterUserRequest.kt
│   │           └── ChangePasswordRequest.kt
│   │
│   └── out
│       └── persistence
│           ├── UserJpaEntity.kt
│           ├── UserJpaRepository.kt
│           └── UserPersistenceAdapter.kt
```

API 매핑
```http
GET    /users/{id}          → GetUserUseCase
POST   /users               → RegisterUserUseCase
PATCH  /users/{id}/password → ChangeUserPasswordUseCase
DELETE /users/{id}          → DeleteUserUseCase
```

의존 방향
```
adapter → application → domain
```

### 2. Clean Architecture + DDD 구조

Clean Architecture는 UseCase 계층을 더 명확하게 분리합니다.

```text
user
├── domain
│   ├── entity
│   │   └── User.kt
│   ├── value
│   │   ├── UserId.kt
│   │   ├── Email.kt
│   │   └── Password.kt
│   ├── enum
│   │   └── UserStatus.kt
│   ├── repository
│   │   └── UserRepository.kt
│   └── service
│       └── UserDomainService.kt
│
├── application
│   ├── usecase
│   │   ├── get
│   │   │   ├── GetUserUseCase.kt
│   │   │   └── GetUserCommand.kt
│   │   ├── register
│   │   │   ├── RegisterUserUseCase.kt
│   │   │   └── RegisterUserCommand.kt
│   │   ├── password
│   │   │   ├── ChangeUserPasswordUseCase.kt
│   │   │   └── ChangeUserPasswordCommand.kt
│   │   └── delete
│   │       ├── DeleteUserUseCase.kt
│   │       └── DeleteUserCommand.kt
│   │
│   └── port
│       └── out
│           └── UserRepositoryPort.kt
│
├── interface
│   └── web
│       ├── UserController.kt
│       └── dto
│           ├── UserResponse.kt
│           ├── RegisterUserRequest.kt
│           └── ChangePasswordRequest.kt
│
└── infrastructure
    └── persistence
        ├── UserJpaEntity.kt
        ├── UserJpaRepository.kt
        └── UserRepositoryAdapter.kt
```

계층 별 특징
```
interface       → Controller, Request, Response
application     → UseCase, Command
domain          → Entity, Value Object, Domain Rule
infrastructure  → DB, JPA, 외부 API
```

### 비교

| Hexagonal + DDD | Clean + DDD |
| --- | --- |
| adapter, application, domain | interface, application, domain, infrastructure |

클린 아키텍처의 계층 의도가 더 명확하다.