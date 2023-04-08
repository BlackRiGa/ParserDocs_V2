package movies.spring.data.neo4j.repositories.repositoryForNeo4j;

import movies.spring.data.neo4j.models.Neo4jModel.ActionModel;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;


public interface ActionRepository extends ReactiveNeo4jRepository<ActionModel, String> {

}