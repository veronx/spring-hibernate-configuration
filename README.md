# Spring Hibernate Configuration

In this repository, you can explore the branches to find out 
different ways of configuring hibernate with spring

## Spring 5.x, Hibernate 5.x and JUnit 5

### Dependencies

- org.springframework:spring-test:5.2.6.RELEASE
  
  - Provide classes to support JUnit 5. (SpringExtension.class and @ContextConfiguration) 
   
- org.junit.jupiter:junit-jupiter-api:5.6.2
- org.junit.jupiterjunit-jupiter-engine:5.6.2
- org.springframework.data:spring-data-jpa:2.3.0.RELEASE
- org.hibernate:hibernate-core:5.4.17.Final
- com.h2database:h2:1.4.200

### Configuration

Firstly, create your spring configuration class and annotate it with @Configuration.

If you are planning to use properties file (ideal) then create the properties file
inside src/main/resources folder, the name can be anyone you like, but try to be concise,
in my case I named it 'persistence.properties'. After that, add the 
@PropertySource("classpath:anyname.properties") annotation to your config class

```java
@Configuration
@PropertySource("classpath:persistence.properties")
public class MainConfig {
 // bean definitions
}
```

To enable Spring Transactional Capabilities, add the following annotation to the configuration
class
```java
@EnableTransactionManagement
```

In case you want to use the Spring Jpa Repositories, add this annotation to your configuration class
```java
@EnableJpaRepositories(basePackages = "com.veronx.repository")
```
The packaged provided to basePackages must be the one with your Repository Interfaces

#### Beans

Now we need to define the beans for the DataSource, the EntityManagerFactory, and
the TransactionManager.

```java
/*
 * Instead of EntityManagerFactory you could also use the Hibernate LocalSessionFactory bean
 * However, using EntityManagerFactory is the JPA Standard and it make easier to change the
 * ORM Framework in the future if needed.
 * And in case you need to use specific hibernate features you can retrieve the session by
 * calling: entityManager.unwrap(Session.class);
 *
 */
@Bean
public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan("com.veronx.entity");
    factory.setJpaProperties(getHibernateProperties());
    return factory;
}

@Bean
public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName", "org.h2.Driver"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    return dataSource;
}

@Bean
public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return txManager;
}
```
