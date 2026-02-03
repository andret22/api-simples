package io.gihub.andret22.simple_api.core.services;

import io.gihub.andret22.simple_api.core.domain.Person;
import io.gihub.andret22.simple_api.core.domain.Team;
import io.gihub.andret22.simple_api.core.ports.input.TeamInputPort;
import io.gihub.andret22.simple_api.core.ports.output.TeamOutputPort;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamRequestDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService implements TeamInputPort {

    private final TeamOutputPort teamOutputPort;

    public TeamService(TeamOutputPort teamOutputPort) {
        this.teamOutputPort = teamOutputPort;
    }

    @Override
    public List<TeamResponseDTO> findAll() {
        return teamOutputPort.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponseDTO findById(UUID id) {
        Team team = teamOutputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return mapToResponseDTO(team);
    }

    @Override
    public TeamResponseDTO create(TeamRequestDTO dto) {
        Team team = new Team();
        team.name = dto.name();

        LocalDate date = LocalDate.now();
        team.created_at = date.toString();
        team.updated_at = date.toString();

        Team savedTeam = teamOutputPort.save(team);
        return mapToResponseDTO(savedTeam);
    }

    @Override
    public TeamResponseDTO update(UUID id, TeamRequestDTO dto) {
        Team team = teamOutputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.name = dto.name();

        Team updatedTeam = teamOutputPort.save(team);
        return mapToResponseDTO(updatedTeam);
    }

    @Override
    public void delete(UUID id) {
        teamOutputPort.deleteById(id);
    }

    private TeamResponseDTO mapToResponseDTO(Team team) {
        List<PersonResponseDTO> people = team.people == null ? Collections.emptyList()
                : team.people.stream()
                        .map(p -> new PersonResponseDTO(p.id, p.name, p.team != null ? p.team.id : null))
                        .collect(Collectors.toList());

        return new TeamResponseDTO(team.id, team.name, people);
    }
}
