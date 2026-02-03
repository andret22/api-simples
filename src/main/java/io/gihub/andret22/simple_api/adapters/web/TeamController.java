package io.gihub.andret22.simple_api.adapters.web;

import io.gihub.andret22.simple_api.core.ports.input.TeamInputPort;
import io.gihub.andret22.simple_api.dtos.team.TeamRequestDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamInputPort teamInputPort;

    public TeamController(TeamInputPort teamInputPort) {
        this.teamInputPort = teamInputPort;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> findAll() {
        return ResponseEntity.ok(teamInputPort.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(teamInputPort.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeamResponseDTO> create(@RequestBody TeamRequestDTO dto) {
        TeamResponseDTO created = teamInputPort.create(dto);
        return ResponseEntity.created(URI.create("/teams/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> update(@PathVariable UUID id, @RequestBody TeamRequestDTO dto) {
        return ResponseEntity.ok(teamInputPort.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        teamInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
