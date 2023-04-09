package movies.spring.data.neo4j.repositories.repositoryForPostgre;

import movies.spring.data.neo4j.models.PostgreModel.UserModelForPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryPostgres extends JpaRepository<UserModelForPostgres, UUID> {
    @Query("select * \n" +
            "from usermodelforpostgres\n" +
            "where localid = :LocalId and form LIKE '%:FORM%'")
    List<UserModelForPostgres> findAllUserModelForPostgresByLocalIDAndForm(@Param("LocalId") Integer localId, @Param("FORM") String form);
}