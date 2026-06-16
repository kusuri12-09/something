package com.example.kusuri.jpa.nplusone;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Team(String name) {
        this.name = name;
    }
}