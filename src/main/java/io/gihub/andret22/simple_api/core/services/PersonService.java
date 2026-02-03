package io.gihub.andret22.simple_api.core.services;

import io.gihub.andret22.simple_api.core.domain.Person;
import io.gihub.andret22.simple_api.core.domain.Team;
import io.gihub.andret22.simple_api.core.ports.input.PersonInputPort;
import io.gihub.andret22.simple_api.core.ports.output.PersonOutputPort;
import io.gihub.andret22.simple_api.core.ports.output.TeamOutputPort;
import io.gihub.andret22.simple_api.dtos.person.PersonRequestDTO;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService implements PersonInputPort {

    private final PersonOutputPort personOutputPort;
    private final TeamOutputPort teamOutputPort;

    public PersonService(PersonOutputPort personOutputPort, TeamOutputPort teamOutputPort) {
        this.personOutputPort = personOutputPort;
        this.teamOutputPort = teamOutputPort;
    }

    @Override
    public List<PersonResponseDTO> findAll() {
        return personOutputPort.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonResponseDTO findById(UUID id) {
        Person person = personOutputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToResponseDTO(person);
    }

    @Override
    public PersonResponseDTO create(PersonRequestDTO dto) {
        Person person = new Person();
        person.name = dto.name();

        if (dto.teamId() != null) {
            Team team = teamOutputPort.findById(dto.teamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            person.team = team;
        }

        Person savedPerson = personOutputPort.save(person);
        return mapToResponseDTO(savedPerson);
    }

    @Override
    public PersonResponseDTO update(UUID id, PersonRequestDTO dto) {
        Person person = personOutputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.name = dto.name();

        if (dto.teamId() != null) {
            Team team = teamOutputPort.findById(dto.teamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            person.team = team;
        } else {
            person.team = null;
        }

        Person updatedPerson = personOutputPort.save(person);
        return mapToResponseDTO(updatedPerson);
    }

    @Override
    public void delete(UUID id) {
        personOutputPort.deleteById(id);
    }

    private PersonResponseDTO mapToResponseDTO(Person person) {
        return new PersonResponseDTO(person.id, person.name, person.team != null ? person.team.id : null);
    }
}
