package com.example.kusuri.jpa.nplusone;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("jpa")
@Component
@RequiredArgsConstructor
@Transactional
public class NPlusOneRunner implements CommandLineRunner {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {

        // Member 별로 팀 하나씩 생성
        for (int i = 1; i <= 100; i++) {
            Team team = new Team("team" + i);
            em.persist(team);

            em.persist(
                    new Member("member" + i, team)
            );
        }

        em.flush();
        em.clear();

        System.out.println("====== Member + Team 조회 ======");

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println(member.getTeam().getName());
        }

        em.clear();

        System.out.println("====== Fetch Join ======");

        List<Member> membersWithTeam = memberRepository.findAllWithTeam();

        for (Member member : membersWithTeam) {
            System.out.println(member.getTeam().getName());
        }
    }
}