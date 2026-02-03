package io.gihub.andret22.simple_api.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.gihub.andret22.simple_api.models.Team;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
