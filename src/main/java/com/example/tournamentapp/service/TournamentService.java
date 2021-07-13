package com.example.tournamentapp.service;

import com.example.tournamentapp.model.Category;
import com.example.tournamentapp.model.Tournament;
import com.example.tournamentapp.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;


    public void addTournament(String tournamentName,
                              String organizer,
                              Integer maxOfParticipant,
                              LocalDate date
                              ){

        tournamentRepository.save(Tournament.builder()
                .name(tournamentName)
                .date(date)
                .prize("")
                .organizer(organizer)
                .description("")
                .maximumNumberOfParticipants(maxOfParticipant)
                .build());
    }
}
