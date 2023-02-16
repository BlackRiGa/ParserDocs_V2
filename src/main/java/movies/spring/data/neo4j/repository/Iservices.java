package movies.spring.data.neo4j.repository;


import movies.spring.data.neo4j.models.WordModel;

import java.util.List;

public interface Iservices<T> {

    T update(T t);

    WordModel save(T t);

    void delete(String id);

    T find(String id);

    List<T> findAll();

}
