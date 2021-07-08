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
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //ENUM uzupełnić
    @Enumerated
    TournamentPhase tournamentPhase;

    @ManyToOne
    Tournament tournament;

    @OneToMany(mappedBy = "phase")
    Set<Game> games;

    //mappedby!!!
}
