CREATE TABLE ModelsInNeo4j
(
    UUID CHARACTER VARYING(128) PRIMARY KEY,
    LocalID INTEGER,
    ID INTEGER,
    FORM CHARACTER VARYING(30),
    FEATS CHARACTER VARYING(30),
    HEAD CHARACTER VARYING(30),
    DEPREL CHARACTER VARYING(30)
);