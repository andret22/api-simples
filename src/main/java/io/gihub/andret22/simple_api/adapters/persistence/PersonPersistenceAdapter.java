package io.gihub.andret22.simple_api.adapters.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.gihub.andret22.simple_api.adapters.persistence.entities.PersonEntity;

import io.gihub.andret22.simple_api.adapters.persistence.repositories.PersonRepository;
import io.gihub.andret22.simple_api.adapters.persistence.repositories.TeamRepository;
import io.gihub.andret22.simple_api.core.domain.Person;
import io.gihub.andret22.simple_api.core.domain.Team;
import io.gihub.andret22.simple_api.core.ports.output.PersonOutputPort;

@Component
public class PersonPersistenceAdapter implements PersonOutputPort {

    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public PersonPersistenceAdapter(PersonRepository personRepository, TeamRepository teamRepository) {
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = toEntity(person);
        PersonEntity savedEntity = personRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return personRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        personRepository.deleteById(id);
    }

    private PersonEntity toEntity(Person person) {
        if (person == null) {
            return null;
        }
        PersonEntity entity = new PersonEntity();
        entity.id = person.id;
        entity.name = person.name;
        entity.created_at = person.created_at;
        entity.updated_at = person.updated_at;
        if (person.team != null) {
            // In a real scenario, we might need to fetch the team entity reference or map
            // it fully.
            // For simplicity, we'll try to map what we have, or fetch if we had a full
            // TeamPersistenceAdapter.
            // But here we are saving a Person, and the relationship is ManyToOne.
            // If the team exists, we should set it.
            if (person.team.id != null) {
                entity.team = teamRepository.getReferenceById(person.team.id);
            }
        }
        return entity;
    }

    private Person toDomain(PersonEntity entity) {
        if (entity == null) {
            return null;
        }
        Person person = new Person();
        person.id = entity.id;
        person.name = entity.name;
        person.created_at = entity.created_at;
        person.updated_at = entity.updated_at;
        if (entity.team != null) {
            Team team = new Team();
            team.id = entity.team.id;
            team.name = entity.team.name;
            team.created_at = entity.team.created_at;
            team.updated_at = entity.team.updated_at;
            person.team = team;
        }
        return person;
    }
}
