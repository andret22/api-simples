package io.gihub.andret22.simple_api.controllers;

import io.gihub.andret22.simple_api.dtos.team.TeamRequestDTO;
import io.gihub.andret22.simple_api.dtos.team.TeamResponseDTO;
import io.gihub.andret22.simple_api.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(teamService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeamResponseDTO> create(@RequestBody TeamRequestDTO dto) {
        TeamResponseDTO created = teamService.create(dto);
        return ResponseEntity.created(URI.create("/teams/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> update(@PathVariable UUID id, @RequestBody TeamRequestDTO dto) {
        return ResponseEntity.ok(teamService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
