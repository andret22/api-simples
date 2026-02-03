package io.gihub.andret22.simple_api.services;

import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamRequestDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamResponseDTO;
import io.gihub.andret22.simple_api.models.Team;
import io.gihub.andret22.simple_api.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamResponseDTO> findAll() {
        return teamRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public TeamResponseDTO findById(UUID id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return mapToResponseDTO(team);
    }

    public TeamResponseDTO create(TeamRequestDTO dto) {
        Team team = new Team();
        team.name = dto.name();

        Team savedTeam = teamRepository.save(team);
        return mapToResponseDTO(savedTeam);
    }

    public TeamResponseDTO update(UUID id, TeamRequestDTO dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.name = dto.name();

        Team updatedTeam = teamRepository.save(team);
        return mapToResponseDTO(updatedTeam);
    }

    public void delete(UUID id) {
        teamRepository.deleteById(id);
    }

    private TeamResponseDTO mapToResponseDTO(Team team) {
        List<PersonResponseDTO> people = team.pessoas == null ? Collections.emptyList()
                : team.pessoas.stream()
                        .map(p -> new PersonResponseDTO(p.id, p.name, p.team != null ? p.team.id : null))
                        .collect(Collectors.toList());

        return new TeamResponseDTO(team.id, team.name, people);
    }
}
