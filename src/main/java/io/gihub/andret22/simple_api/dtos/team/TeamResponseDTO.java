package io.gihub.andret22.simple_api.dtos.team;

import java.util.List;
import java.util.UUID;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;

public record TeamResponseDTO(UUID id, String name, List<PersonResponseDTO> people) {
}
