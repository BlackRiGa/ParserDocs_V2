package movies.spring.data.neo4j.models.Neo4jModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node("Movie")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Property("tagline")
    private String description;
    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private Set<PersonModel> actors = new HashSet<>();
    @Relationship(type = "DIRECTED", direction = INCOMING)
    private Set<PersonModel> directors = new HashSet<>();
}