package com.example.kusuri.lifecycle;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("lifecycle")
@Component
public class LifeCycleRunner implements CommandLineRunner {

    private final LifeCycleBean lifeCycleBean;

    public LifeCycleRunner(LifeCycleBean lifeCycleBean) {
        this.lifeCycleBean = lifeCycleBean;
    }

    @Override
    public void run(String... args) {
        lifeCycleBean.businessLogic();
    }
}
