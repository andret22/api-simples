package io.gihub.andret22.simple_api.services;

import io.gihub.andret22.simple_api.dtos.person.PersonRequestDTO;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import io.gihub.andret22.simple_api.models.Person;
import io.gihub.andret22.simple_api.models.Team;
import io.gihub.andret22.simple_api.repositories.PersonRepository;
import io.gihub.andret22.simple_api.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public PersonService(PersonRepository personRepository, TeamRepository teamRepository) {
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public PersonResponseDTO findById(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToResponseDTO(person);
    }

    public PersonResponseDTO create(PersonRequestDTO dto) {
        Person person = new Person();
        person.name = dto.name();

        if (dto.teamId() != null) {
            Team team = teamRepository.findById(dto.teamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            person.team = team;
        }

        Person savedPerson = personRepository.save(person);
        return mapToResponseDTO(savedPerson);
    }

    public PersonResponseDTO update(UUID id, PersonRequestDTO dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.name = dto.name();

        if (dto.teamId() != null) {
            Team team = teamRepository.findById(dto.teamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            person.team = team;
        } else {
            person.team = null;
        }

        Person updatedPerson = personRepository.save(person);
        return mapToResponseDTO(updatedPerson);
    }

    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    private PersonResponseDTO mapToResponseDTO(Person person) {
        return new PersonResponseDTO(person.id, person.name, person.team != null ? person.team.id : null);
    }
}
