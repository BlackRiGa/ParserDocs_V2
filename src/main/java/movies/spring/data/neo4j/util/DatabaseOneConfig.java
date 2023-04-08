//package movies.spring.data.neo4j.util;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@EnableJpaRepositories(
//        entityManagerFactoryRef = DatabaseOneConfig.ENTITY_MANAGER_FACTORY,
//        transactionManagerRef = DatabaseOneConfig.TRANSACTION_MANAGER,
//        basePackages = DatabaseOneConfig.JPA_REPOSITORY_PACKAGE
//)
//@Configuration
//public class DatabaseOneConfig {
//
//    public static final String PROPERTY_PREFIX = "app.dbone.datasource";
//    public static final String JPA_REPOSITORY_PACKAGE = "movies.spring.data.neo4j.repositories.repositoryForNeo4j";
//    public static final String ENTITY_PACKAGE = "movies.spring.data.neo4j.models.Neo4jModel";
//    public static final String ENTITY_MANAGER_FACTORY = "oneEntityManagerFactory";
//    public static final String DATA_SOURCE = "oneDataSource";
//    public static final String DATABASE_PROPERTY = "oneDatabaseProperty";
//    public static final String TRANSACTION_MANAGER = "oneTransactionManager";
//    @Bean(DATABASE_PROPERTY)
//    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
//    public DatabaseProperty appDatabaseProperty() {
//        return new DatabaseProperty();
//    }
//
//
//    @Bean(DATA_SOURCE)
//    public DataSource appDataSource(
//            @Qualifier(DATABASE_PROPERTY) DatabaseProperty databaseProperty
//    ) {
//        return DataSourceBuilder
//                .create()
//                .username(databaseProperty.getUsername())
//                .password(databaseProperty.getPassword())
//                .url(databaseProperty.getUrl())
//                .build();
//    }
//
//    @Bean(ENTITY_MANAGER_FACTORY)
//    public LocalContainerEntityManagerFactoryBean appEntityManager(
//            @Qualifier(DATA_SOURCE) DataSource dataSource
//    ) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
//        em.setPackagesToScan(ENTITY_PACKAGE);
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("apoc.import.file.enabled", "true");
//        properties.put("apoc.import.file.use_neo4j_config", "false");
//        properties.put("logging.level.org.springframework.data.neo4j.cypher", "trace");
//
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean(TRANSACTION_MANAGER)
//    public PlatformTransactionManager sqlSessionTemplate(
//            @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManager,
//            @Qualifier(DATA_SOURCE) DataSource dataSource
//    ) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManager.getObject());
//        transactionManager.setDataSource(dataSource);
//        return transactionManager;
//    }
//
//}
