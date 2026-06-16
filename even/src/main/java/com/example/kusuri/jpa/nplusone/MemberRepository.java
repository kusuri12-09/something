package com.example.kusuri.jpa.nplusone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
        select m
        from Member m
        join fetch m.team
    """)
    List<Member> findAllWithTeam();
}
