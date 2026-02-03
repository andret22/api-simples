package io.gihub.andret22.simple_api.dtos.person;

import java.util.UUID;

public record PersonRequestDTO(String name, UUID teamId) {
}
