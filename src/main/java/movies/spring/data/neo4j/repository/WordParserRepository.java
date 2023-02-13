package movies.spring.data.neo4j.repository;

import movies.spring.data.neo4j.models.WordModel;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordParserRepository extends CrudRepository<WordModel, String> {

    List<WordModel> findAll();

    @Query("MATCH (t:WordModel) - [r:RELATED_TO] -> () RETURN t, COUNT(r) AS numberOfRelations")
    List<WordModel> findAllNodesFromQuery();

    @Query(" CALL apoc.load.json(\"file:///person.json\")\n" +
            "        YIELD value\n" +
            "        MERGE (p:Person {name: value.name})\n" +
            "        SET p.age = value.age\n" +
            "        WITH p, value\n" +
            "        UNWIND value.children AS child\n" +
            "        MERGE (c:Person {name: child})\n" +
            "        MERGE (c)-[:CHILD_OF]->(p);")
    List<WordModel> findAllWords();
}

