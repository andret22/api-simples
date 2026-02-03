package io.gihub.andret22.simple_api.core.ports.input;

import java.util.List;
import java.util.UUID;

import io.gihub.andret22.simple_api.dtos.person.PersonRequestDTO;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;

public interface PersonInputPort {
    List<PersonResponseDTO> findAll();

    PersonResponseDTO findById(UUID id);

    PersonResponseDTO create(PersonRequestDTO dto);

    PersonResponseDTO update(UUID id, PersonRequestDTO dto);

    void delete(UUID id);
}
