package movies.spring.data.neo4j.models.PostgreModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class UserModelForPostgres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idForPostgres;
    private String UUID;
    private Integer localID;
    private Integer id;
    private String form;
    private String head;
    private String feats;
    private String deprel;

}

