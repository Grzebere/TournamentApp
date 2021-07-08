package com.example.tournamentapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Boolean isFinished;
    Double teamTwoPoints;
    Double teamOnePoints;
    LocalDate date;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Team teamOne;

    @ManyToOne
    private Team teamTwo;

    @ManyToOne
    private Phase phase;

// mappedBy ???

}
