package io.gihub.andret22.simple_api.core.ports.input;

import java.util.List;
import java.util.UUID;

import io.gihub.andret22.simple_api.dtos.team.TeamRequestDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamResponseDTO;

public interface TeamInputPort {
    List<TeamResponseDTO> findAll();

    TeamResponseDTO findById(UUID id);

    TeamResponseDTO create(TeamRequestDTO dto);

    TeamResponseDTO update(UUID id, TeamRequestDTO dto);

    void delete(UUID id);
}
