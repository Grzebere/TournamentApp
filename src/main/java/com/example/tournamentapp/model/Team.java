package com.example.tournamentapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "team")
    private Set<Tournament> tournaments;

    @OneToMany(mappedBy = "teamOne")
    private Set<Game> gamesAsTeamOne;

    @OneToMany(mappedBy = "teamTwo")
    private Set<Game> gamesAsTeamTwo;

    @ManyToMany(mappedBy = "team")
    private Set<User> users;

}
