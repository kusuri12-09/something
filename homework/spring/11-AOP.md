# AOP란?

AOP(Aspect-Oriented Programming)는 여러 클래스에 공통적으로 들어가는 코드를 분리하기 위한 기술입니다.

Spring에서는 주로 다음과 같은 기능이 AOP로 구현됩니다.

* 트랜잭션 (@Transactional)
* 비동기 처리 (@Async)
* 캐싱 (@Cacheable)
* 메서드 보안 (@PreAuthorize)
* 로깅
* 실행 시간 측정
* 모니터링 및 메트릭 수집

AOP를 이용하면 클래스마다 반복되는 코드를 줄일 수 있습니다.

```java
public User getUser() {
    long start = System.currentTimeMillis();

    try {
        return repository.findById(1L);
    } finally {
        log.info("time={}", System.currentTimeMillis() - start);
    }
}
```

위처럼 모든 Service 메서드에 실행 시간을 측정한다고 할 때, 계속 반복되는 코드를 한곳에서 처리합니다.

```java
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            log.info("time={}", System.currentTimeMillis() - start);
        }
    }
}
```

## AOP 용어
| 용어                         | 설명                                                | 예시                                         |
| -------------------------- | ------------------------------------------------- | ------------------------------------------ |
| **Aspect**                 | 공통 관심사(Cross-Cutting Concern)를 모아둔 클래스            | `LoggingAspect`, `TransactionAspect`       |
| **Join Point**             | AOP가 개입할 수 있는 지점. Spring AOP에서는 **메서드 실행 시점만 지원** | `userService.createUser()` 호출              |
| **Advice**                 | Join Point에서 실제로 수행될 부가 기능                        | 로그 출력, 실행 시간 측정, 트랜잭션 시작/종료                |
| **Before Advice**          | 대상 메서드 실행 전에 수행                                   | 인증 검사, 요청 로그 기록                            |
| **After Returning Advice** | 대상 메서드가 정상 종료된 후 수행                               | 결과값 로깅, 캐시 저장                              |
| **After Throwing Advice**  | 대상 메서드에서 예외 발생 시 수행                               | 예외 로깅, 알림 발송                               |
| **After (Finally) Advice** | 성공/실패 여부와 관계없이 항상 수행                              | 리소스 정리                                     |
| **Around Advice**          | 메서드 실행 전후를 모두 제어 가능                               | 실행 시간 측정, 트랜잭션 처리                          |
| **Pointcut**               | Advice를 적용할 Join Point를 선택하는 조건식                  | `execution(* com.example.service.*.*(..))` |
| **Target Object**          | 실제 비즈니스 로직이 담긴 객체                                 | `UserService`                              |
| **AOP Proxy**              | Target Object를 감싸서 Advice를 적용하는 객체                | `@Transactional`이 적용된 Service Bean         |
| **Weaving**                | Aspect를 Target Object에 연결하는 과정                    | Spring 실행 시 프록시 생성                         |
| **Introduction**           | 기존 클래스에 새로운 인터페이스나 기능을 추가하는 특수한 AOP 기법            | `Auditable` 인터페이스 추가                       |
| **Cross-Cutting Concern**  | 여러 클래스에 공통으로 필요한 기능                               | 로깅, 보안, 트랜잭션, 캐싱                           |

### 1. Aspect

공통 관심사를 모아놓은 클래스.

```java
@Aspect
@Component
public class LoggingAspect {
}
```

* LoggingAspect
* TransactionAspect
* SecurityAspect

### 2. Join Point

AOP가 개입할 수 있는 지점

Spring AOP에서는 무조건 메서드 실행 시점만 Join Point입니다.

```java
userService.createUser();
```
위 메서드 호출이 join point

Spring AOP는 AspectJ와는 달리 생성자, 필드 접근, 객체 생성에는 개입하지 못합니다.

### 3. Pointcut

어떤 Join Point에 AOP를 적용할지 선택하는 조건식.

```java
@Pointcut(
    "execution(* com.example.service.*.*(..))"
)
```

의미: 
- com.example.service 패키지의
- 모든 클래스의
- 모든 메서드

표현식 종류 
- `execution(* *..service.*.*(..))`
- `@annotation(Transactional)`
- `within(com.example.service..*)`

### 4. Advice

실제로 실행되는 코드

#### Before

메서드 실행 전

```
@Before(...)
```

#### AfterReturning

정상 종료 후

```
@AfterReturning(...)
```

- 캐시 저장
- 결과 로깅

#### Around ⭐

메서드 전후를 모두 제어 가능

```java
@Around(...)
```

```java
@Around(...)
public Object log(
    ProceedingJoinPoint joinPoint
) throws Throwable {

    log.info("start");

    Object result = joinPoint.proceed();

    log.info("end");

    return result;
}
```

- 실행 시간 측정
- 트랜잭션
- 캐시
- 재시도

## AOP의 핵심

> AOP는 Proxy 기반이다.

Spring AOP는 실제 객체를 수정하지 않습니다.

대신 프록시 객체를 생성합니다

```
Client
  ↓
Proxy
  ↓
Target Object
```

트랜잭셔널 동작 예시
```java
@Transactional
public void createUser() {
}
```
동작 과정
1. 트랜잭션 시작
2. createUser()
3. 트랜잭션 종료

### 문제점

#### Self Invocation

자기 호출

```java
@Service
public class UserService {

    public void a() {
        b();
    }

    @Transactional
    public void b() {
    }
}
```

`a()`를 호출해도 `b()`에 트랜잭션이 적용되지 않습니다.

`this.b()` 는 프록시를 거치지 않기 때문입니다.

## 마무리

Spring AOP는 비즈니스 로직과 공통 기능을 분리하여 코드의 재사용성과 유지보수성을 높이기 위한 기술입니다.

실무에서는 직접 AOP를 구현하기보단 `@Transactional`, `@Async`, `@Cacheable` 과 같은 Spring이 제공하는 기능을 사용하는 경우가 훨씬 많습니다.

따라서 AOP의 세부 구현보다는 프록시 기반으로 동작한다는 점, Join Point가 메서드 실행에 한정된다는 점, Pointcut과 Advice의 역할을 이해하는 것이 중요합니다.