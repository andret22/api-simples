package io.gihub.andret22.simple_api.controllers;

import io.gihub.andret22.simple_api.dtos.person.PersonRequestDTO;
import io.gihub.andret22.simple_api.dtos.person.PersonResponseDTO;
import io.gihub.andret22.simple_api.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@RequestBody PersonRequestDTO dto) {
        PersonResponseDTO created = personService.create(dto);
        return ResponseEntity.created(URI.create("/people/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable UUID id, @RequestBody PersonRequestDTO dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
