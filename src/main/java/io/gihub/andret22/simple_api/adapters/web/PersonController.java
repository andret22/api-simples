package io.gihub.andret22.simple_api.adapters.web;

import io.gihub.andret22.simple_api.core.ports.input.PersonInputPort;
import io.gihub.andret22.simple_api.dtos.person.PersonRequestDTO;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonInputPort personInputPort;

    public PersonController(PersonInputPort personInputPort) {
        this.personInputPort = personInputPort;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> findAll() {
        return ResponseEntity.ok(personInputPort.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(personInputPort.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@RequestBody PersonRequestDTO dto) {
        PersonResponseDTO created = personInputPort.create(dto);
        return ResponseEntity.created(URI.create("/people/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable UUID id, @RequestBody PersonRequestDTO dto) {
        return ResponseEntity.ok(personInputPort.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
