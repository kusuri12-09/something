# API 명세서 작성하기

## API 명세서를 작성하는 2가지 방법

### 1. Notion 등에 작성하기

- 프론트와 백엔드를 병행하면서 개발이 가능하다
- API가 변경될 경우 업데이트해줘야 한다.

### 2. OpenAPI 사용하기

- 백엔드 컨트롤러를 통해 작성하는 방법
- API 명세서를 따로 적지 않아도 되고 테스트도 바로 해 볼 수 있다.
- 서버가 먼저 개발되어야 프론트 개발이 가능하다.

<details>
<summary>API 명세서 템플릿</summary>

## REQUEST

### Path Parameter

| 이름 | 타입 | 필수 | 설명 |
|--------|--------|--------|--------|
| userId | Long | O | 사용자 ID |

### Header

| 이름 | 값 |
|--------|--------|
| Authorization | Bearer {token} |

```json
{
  "email": "user@example.com",
  "password": "Password1!"
}
```

## RESPONSE `200`

```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGci...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "userId": "user_123",
      "email": "user@example.com",
      "status": "ACTIVE"
    }
  },
  "error": null
}
```

## 에러 코드

| 코드 | HTTP Status | 설명 |
| --- | --- | --- |
| `INVALID_CREDENTIALS` | 401 | 이메일 또는 비밀번호 불일치 |
| `ACCOUNT_LOCKED` | 423 | 로그인 5회 실패로 계정 잠금 |
| `EMAIL_NOT_VERIFIED` | 403 | 이메일 미인증 계정 |
</details>

## API 공통 규약 (example)

| 항목                 | 규약                                 |
| ------------------ | ---------------------------------- |
| Protocol           | HTTPS                              |
| Content-Type       | `application/json`                 |
| Character Encoding | UTF-8                              |
| 인증 방식              | Bearer Token (JWT)                 |
| 날짜 형식              | ISO 8601 (`yyyy-MM-dd'T'HH:mm:ss`) |
| 시간 기준              | UTC 또는 KST                         |
| HTTP Method        | GET, POST, PUT, PATCH, DELETE      |
| 응답 형식              | JSON                               |
| 성공 응답              | HTTP Status Code + Response Body   |
| 실패 응답              | HTTP Status Code + Error Response  |
| Nullable 표기        | `Y` / `N`                          |
| 필수 여부 표기           | `Required: Y` / `N`                |

### 공통 응답 구조
#### 성공 응답
```json
{
  "success": true,
  "data": {}
}
```
#### 실패 응답
```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "에러 메시지"
  }
}
```

### 공통 HTTP Status

| Status                    | 의미              |
| ------------------------- | --------------- |
| 200 OK                    | 요청 성공           |
| 201 Created               | 리소스 생성 성공       |
| 204 No Content            | 요청 성공, 응답 본문 없음 |
| 400 Bad Request           | 잘못된 요청          |
| 401 Unauthorized          | 인증 실패           |
| 403 Forbidden             | 권한 없음           |
| 404 Not Found             | 리소스 없음          |
| 409 Conflict              | 중복 또는 충돌        |
| 500 Internal Server Error | 서버 오류           |
