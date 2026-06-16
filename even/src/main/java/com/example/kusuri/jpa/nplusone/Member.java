package com.example.kusuri.jpa.nplusone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Member(String name, Team team) {
        this.name = name;
        this.team = team;
    }
}