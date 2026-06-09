# ApplicationContext 확장시키기

일반적으로 애플리케이션 개발자는 ApplicationContext 구현 클래스를 직접 상속(subclass)할 필요가 없습니다.

대신, Spring IoC 컨테이너는 특수한 통합(Integration) 인터페이스의 구현체를 등록하는 방식으로 확장할 수 있습니다.

## BeanPostProcessor로 빈 커스텀하기

BeanPostProcessor 인터페이스는 개발자가 직접 구현하여 사용할 수 있는 콜백 메서드를 정의합니다.

이를 통해 Spring 컨테이너의 기본 Bean 생성 과정에 사용자 정의 로직을 추가할 수 있습니다.

Spring 컨테이너가 Bean의 인스턴스 생성과 의존성 주입을 완료한 뒤, 초기화 과정 전후에 추가적인 로직을 실행하고 싶다면 하나 이상의 커스텀 BeanPostProcessor 구현체를 등록할 수 있습니다.

BeanPostProcessor는 Bean의 초기화 과정 전/후에 끼어들어 로직을 처리할 수 있으며, Spring의 AOP, `@Transactional`, `@Autowired` 등의 기능도 이를 기반으로 구현되어 있습니다.

> [!NOTE]
> BeanPostProcessor 인스턴스는 컨테이너 단위로 적용되며, Bean(객체) 인스턴스 자체를 대상으로 동작합니다.
>
> 즉, Spring IoC 컨테이너가 먼저 Bean 인스턴스를 생성한 후, BeanPostProcessor가 해당 Bean에 대한 후처리 작업을 수행합니다.
>
> BeanDefinition를 수정하려면 `BeanFactoryPostProcessor`를 사용해야 합니다.

BeanPostProcessor는 Bean 초기화 전후에 호출되는 두 개의 콜백 메서드를 제공합니다.

```java
postProcessBeforeInitialization()
postProcessAfterInitialization()
```

ApplicationContext는 BeanPostProcessor를 구현한 Bean을 자동으로 탐지하여 등록합니다. 또한, 모든 Bean 생성 시 자동 호출됩니다.

BeanPostProcessor는 Bean 초기화 과정에 개입하여 Bean을 수정하거나 프록시로 감싸는 등의 후처리를 수행할 수 있습니다.


`@Bean`으로 BeanPostProcessor를 등록할 경우 반환 타입은 구현 클래스 또는 BeanPostProcessor 인터페이스로 선언하는 것이 권장됩니다.
