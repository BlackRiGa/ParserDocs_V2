package movies.spring.data.neo4j.repositories.repositoryForPostgre;

import movies.spring.data.neo4j.models.PostgreModel.UserModelForPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserRepositoryPostgres extends JpaRepository<UserModelForPostgres, UUID> {
}