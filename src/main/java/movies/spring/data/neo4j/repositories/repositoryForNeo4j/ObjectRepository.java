package movies.spring.data.neo4j.repositories.repositoryForNeo4j;

import movies.spring.data.neo4j.models.Neo4jModel.ObjectModel;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ObjectRepository extends ReactiveNeo4jRepository<ObjectModel, String> {
}