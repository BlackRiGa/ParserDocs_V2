package movies.spring.data.neo4j.models.Neo4jModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("ObjectModel")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectModel {
    @Id
    private String uuid;
    private String localID;
    private String form;
    private String id;
    private String head;
    private String feats;
    private String deprel;
}
