package io.gihub.andret22.simple_api.adapters.persistence.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.gihub.andret22.simple_api.adapters.persistence.entities.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
