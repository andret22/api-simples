package io.gihub.andret22.simple_api.adapters.persistence.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.gihub.andret22.simple_api.adapters.persistence.entities.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
}
