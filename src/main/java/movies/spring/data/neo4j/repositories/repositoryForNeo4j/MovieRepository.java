package movies.spring.data.neo4j.repositories.repositoryForNeo4j;

import movies.spring.data.neo4j.models.Neo4jModel.MovieModel;
import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovieRepository extends ReactiveNeo4jRepository<MovieModel, Long> {
    Mono<MovieModel> findOneByTitle(String title);

    @Query("MATCH (n:UserModel) WHERE n.form = $name RETURN n")
    List<UserModel> findAllByName(String name);
}