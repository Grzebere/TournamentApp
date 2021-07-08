package com.example.tournamentapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    LocalDate date;
    String name;
    String prize;
    String organizer;
    String description;
    String maximumNumberOfParticipants;

    // ENUM!!! odpowiednio obsłużyć
    @Enumerated
    Category category;

    @ManyToMany()
    private Set<Team> teams;

    @OneToMany(mappedBy = "tournament")
    private Set<Phase> phases;

    @OneToMany(mappedBy = "tournament")
    private Set<Game> games;

// mapowania? sprawdzić

}
