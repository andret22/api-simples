package io.gihub.andret22.simple_api.core.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.gihub.andret22.simple_api.core.domain.Team;

public interface TeamOutputPort {
    Team save(Team team);

    Optional<Team> findById(UUID id);

    List<Team> findAll();

    void deleteById(UUID id);
}
