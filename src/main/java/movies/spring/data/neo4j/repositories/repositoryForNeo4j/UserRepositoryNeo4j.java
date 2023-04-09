package movies.spring.data.neo4j.repositories.repositoryForNeo4j;

import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

import java.util.Optional;


public interface UserRepositoryNeo4j extends ReactiveNeo4jRepository<UserModel, String> {
    @Query("MATCH (n:UserModel) WHERE n.form = $Form and n.localId = $LocalId RETURN n")
    Flux<UserModel> findAllUserModelByLocalIDAndForm(String Form, String LocalId);

}

