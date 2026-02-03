package io.gihub.andret22.simple_api.core.ports.output;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.gihub.andret22.simple_api.core.domain.Person;

public interface PersonOutputPort {
    Person save(Person person);

    Optional<Person> findById(UUID id);

    List<Person> findAll();

    void deleteById(UUID id);
}
