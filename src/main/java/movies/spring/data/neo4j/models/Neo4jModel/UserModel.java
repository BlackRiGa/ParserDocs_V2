package movies.spring.data.neo4j.models.Neo4jModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("UserModel")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    private String UUID;
    private String localID;
    private String form;
    private String id;
    private String head;
    private String feats;
    private String deprel;
}
