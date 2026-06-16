package com.example.kusuri.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleBean implements InitializingBean, DisposableBean {

    public LifeCycleBean() {
        System.out.println("1. 생성자 호출");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("2. @PostConstruct 호출");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("3. InitializingBean.afterPropertiesSet 호출");
    }

    public void businessLogic() {
        System.out.println("4. 비즈니스 로직 실행");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("5. @PreDestroy 호출");
    }

    @Override
    public void destroy() {
        System.out.println("6. DisposableBean.destroy 호출");
    }
}