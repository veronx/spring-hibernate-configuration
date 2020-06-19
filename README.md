# Spring Hibernate Configuration

## Spring Boot 2.3.1

### Dependencies

- org.springframework.boot:spring-boot-starter-parent:2.3.1.RELEASE
- org.springframework.boot:spring-boot-starter-data-jpa
- org.springframework.boot:spring-boot-starter-test
- org.springframework.boot:spring-boot-maven-plugin
- com.h2database

### Configuration

Due to auto-configuration nature of Spring Boot, the JPA configuration can be done
entirely throughout the application.properties file

```properties
spring.datasource.url=jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

For other properties, please refer to [Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#data-properties)

#### Enable Transaction Management
The annotation @EnableTransactionManagement is not required in Spring Boot whenever
the classpath contains either spring-data-* or spring-tx dependency.
