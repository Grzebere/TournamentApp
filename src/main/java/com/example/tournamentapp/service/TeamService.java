package com.example.tournamentapp.service;

import com.example.tournamentapp.exception.InvalidIdAddress;
import com.example.tournamentapp.model.Account;
import com.example.tournamentapp.model.Team;
import com.example.tournamentapp.model.Tournament;
import com.example.tournamentapp.repository.AccountRepository;
import com.example.tournamentapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final AccountRepository accountRepository;


    public void addTeam(String name, Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            teamRepository.save(Team.builder()
                    .name(name)
                    .accounts(Set.<Account>of(accountOptional.get()))
                    .build());
        }
    }

    public void addTeammate(Long teamId, Long accountId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (teamOptional.isPresent() && accountOptional.isPresent()) {
            Team team = teamOptional.get();
            team.getAccounts().add(accountOptional.get());

            teamRepository.save(team);
        }
    }

    public void removeTeammate(Long teamId, Long accountId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (teamOptional.isPresent() && accountOptional.isPresent()) {
            if (teamOptional.get().getAccounts().contains(accountOptional.get())) {
                Team team = teamOptional.get();
                team.getAccounts().remove(accountOptional.get());

                teamRepository.save(team);
            }
        }
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Tournament> getTournamentListForTeam(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            return List.copyOf(teamOptional.get().getTournaments());
        }
        throw new InvalidIdAddress("No team with that Id in database");
    }


}
