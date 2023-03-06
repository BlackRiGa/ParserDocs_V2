package movies.spring.data.neo4j.repository;

import movies.spring.data.neo4j.models.WordModel;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface WordParserRepository extends ReactiveNeo4jRepository<WordModel, String> {
    Mono<WordModel> findOneById(String id);

//    @Query("MATCH (t:WordModel) - [r:RELATED_TO] -> () RETURN t, COUNT(r) AS numberOfRelations")
//    List<WordModel> findAllNodesFromQuery();

//    @Query(" CALL apoc.load.json(\"file:///person.json\")\n" +
//            "        YIELD value\n" +
//            "        MERGE (p:Person {name: value.name})\n" +
//            "        SET p.age = value.age\n" +
//            "        WITH p, value\n" +
//            "        UNWIND value.children AS child\n" +
//            "        MERGE (c:Person {name: child})\n" +
//            "        MERGE (c)-[:CHILD_OF]->(p);")
//    List<WordModel> findAllWords();
}

