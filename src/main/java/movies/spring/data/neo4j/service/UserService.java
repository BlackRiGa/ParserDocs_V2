package movies.spring.data.neo4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import movies.spring.data.neo4j.models.Neo4jModel.UserModel;
import movies.spring.data.neo4j.models.PostgreModel.UserModelForPostgres;
import movies.spring.data.neo4j.repositories.repositoryForPostgre.UserRepositoryPostgres;
import movies.spring.data.neo4j.util.DatabaseTwoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    private final UserRepositoryPostgres userRepositoryPostgres;
    private final DatabaseTwoConfig databaseTwoConfig;
    @Autowired
    public UserService(UserRepositoryPostgres userRepositoryPostgres, DatabaseTwoConfig databaseTwoConfig) {
        this.userRepositoryPostgres = userRepositoryPostgres;
        this.databaseTwoConfig = databaseTwoConfig;
    }
}
