# N+1 문제란?

데이터를 조회할 때 처음 1번의 쿼리로 목록(N개)을 가져오고, 그 이후 각 데이터마다 추가 쿼리가 발생하는 문제입니다.

member 전체 데이터 100개를 조회했는데, 
각 member의 team 정보를 조회하기 위해 100번의 쿼리를 추가 실행

```sql
-- 회원 전체 조회
SELECT * FROM member;

-- 회원 100명에 대해
SELECT * FROM team WHERE id = 1;
SELECT * FROM team WHERE id = 2;
SELECT * FROM team WHERE id = 3;
...
```
총 1+100회가 실행되어 N+1문제라고 부릅니다.

## 발생 이유?

주로 JPA의 지연 로딩(Lazy) 때문에 발생합니다.

```java
@Entity
public class Member {

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}
```

```java
List<Member> members = memberRepository.findAll();

for (Member member : members) {
    System.out.println(member.getTeam().getName());
}
```

### 실행 과정

1. member 목록 조회
```sql
SELECT * FROM member;
```
2. team 정보 접근
```java
member.getTeam().getName()
```

이 시점에 프록시가 실제 데이터를 가져오기 위해 아래 SQL을 실행합니다.

```sql
SELECT * FROM team WHERE id = 1;
SELECT * FROM team WHERE id = 2;
...
```

## 문제점

### 1. 성능 저하

쿼리가 급격히 많아집니다.

만약 member 데이터가 수천, 수만건이라면...

### 2. 응답 시간 증가

```http
GET /members
```
한 번 호출할 때 수백~수천 개의 SQL이 발생할 수 있습니다.

### 3. DB 부하 증가

동시 접속자가 많아질수록 문제가 심화됩니다.

## EAGER를 사용하면 안되나요?

```java
@ManyToOne(fetch = FetchType.EAGER)
```
발생할 수 있습니다.

LAZY/EAGER는 "언제 가져올 것인가"를 결정할 뿐,

"JOIN으로 가져올 것인가"를 보장하지 않습니다.

## 해결 방법

### 1. Fetch Join

```java
@Query("""
    select m
    from Member m
    join fetch m.team
""")
List<Member> findAllWithTeam();
```

```sql
SELECT m.*, t.*
FROM member m
JOIN team t
ON m.team_id = t.id;
```
쿼리 1번으로 데이터를 가져올 수 있습니다.

### 2. EntityGraph

```java
@EntityGraph(attributePaths = "team")
List<Member> findAll();
```

내부적으로 Fetch Join과 비슷하게 동작합니다.

fetch join이 더 직관적인 면이 있어서 fetch join을 더 많이 사용합니다.

### 3. Batch Size
```java
@BatchSize(size = 100)
```

```properties
spring.jpa.properties.hibernate.default_batch_fetch_size=100
```
개선:
```sql
SELECT * FROM team
WHERE id IN (1,2,3,...100);
```

## 한 줄 요약

> N+1 문제는 연관 엔티티 조회 과정에서 1번의 조회 쿼리 + N번의 추가 조회 쿼리가 발생하여 성능이 크게 저하되는 문제입니다.