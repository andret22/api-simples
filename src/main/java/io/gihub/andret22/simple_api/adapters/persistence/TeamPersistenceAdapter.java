package io.gihub.andret22.simple_api.adapters.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.gihub.andret22.simple_api.adapters.persistence.entities.PersonEntity;
import io.gihub.andret22.simple_api.adapters.persistence.entities.TeamEntity;
import io.gihub.andret22.simple_api.adapters.persistence.repositories.TeamRepository;
import io.gihub.andret22.simple_api.core.domain.Person;
import io.gihub.andret22.simple_api.core.domain.Team;
import io.gihub.andret22.simple_api.core.ports.output.TeamOutputPort;

@Component
public class TeamPersistenceAdapter implements TeamOutputPort {

    private final TeamRepository teamRepository;

    public TeamPersistenceAdapter(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team save(Team team) {
        TeamEntity entity = toEntity(team);
        TeamEntity savedEntity = teamRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Team> findById(UUID id) {
        return teamRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        teamRepository.deleteById(id);
    }

    private TeamEntity toEntity(Team team) {
        if (team == null) {
            return null;
        }
        TeamEntity entity = new TeamEntity();
        entity.id = team.id;
        entity.name = team.name;
        entity.created_at = team.created_at;
        entity.updated_at = team.updated_at;
        // People list is usually not saved cascade from Team side in this simple
        // example unless CascadeType is set.
        // For now we map it if present but usually we manage relationship from the Many
        // side (Person).
        return entity;
    }

    private Team toDomain(TeamEntity entity) {
        if (entity == null) {
            return null;
        }
        Team team = new Team();
        team.id = entity.id;
        team.name = entity.name;
        team.created_at = entity.created_at;
        team.updated_at = entity.updated_at;
        if (entity.people != null) {
            team.people = entity.people.stream().map(this::mapPersonSummary).collect(Collectors.toList());
        }
        return team;
    }

    private Person mapPersonSummary(PersonEntity entity) {
        Person person = new Person();
        person.id = entity.id;
        person.name = entity.name;
        // Avoid infinite recursion by not mapping team back here or just mapping ID
        return person;
    }
}
