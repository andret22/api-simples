package io.gihub.andret22.simple_api.dtos.person;

import java.util.UUID;

public record PersonResponseDTO(UUID id, String name, UUID teamId) {
}
