package movies.spring.data.neo4j.repositories.repositoryForNeo4j;

import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserRepositoryNeo4j extends ReactiveNeo4jRepository<UserModel, String> {
    @Query("MATCH (n:UserModel) WHERE n.form = $name RETURN n")
    List<UserModel> findAllByName(String name);
}

