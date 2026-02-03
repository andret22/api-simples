package io.gihub.andret22.simple_api.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.gihub.andret22.simple_api.models.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
