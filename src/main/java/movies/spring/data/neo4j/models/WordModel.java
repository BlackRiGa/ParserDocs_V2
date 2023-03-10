package movies.spring.data.neo4j.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@Node("WordModel")
public class WordModel {
    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private final List<WordModel> directors = new ArrayList<>();
    @Id
    private String localID;
    private String id;
    private String form;
    private String lemma;
    private String postag;
    private String feats;
    private String head;
    private String deprel;

    public WordModel(String id, String localId, String form, String lemma, String postag, String feats, String head, String deprel) {
        this.id = id;
        this.form = form;
        this.lemma = lemma;
        this.postag = postag;
        this.feats = feats;
        this.head = head;
        this.deprel = deprel;
    }


    public String getLocal_id() {
        return localID;
    }

    public void setLocal_id(String local_id) {
        this.localID = localID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPostag() {
        return postag;
    }

    public void setPostag(String postag) {
        this.postag = postag;
    }

    public String getFeats() {
        return feats;
    }

    public void setFeats(String feats) {
        this.feats = feats;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDeprel() {
        return deprel;
    }

    public void setDeprel(String deprel) {
        this.deprel = deprel;
    }
}
